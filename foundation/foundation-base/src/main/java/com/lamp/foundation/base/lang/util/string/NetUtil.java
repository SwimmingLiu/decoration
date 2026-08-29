package com.lamp.foundation.base.lang.util.string;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Enumeration;

import com.lamp.foundation.base.lang.lock.ConciseLock;

public class NetUtil {


    public static final String OS_NAME = System.getProperty("os.name");

    private static final ConciseLock<String> LOAD_ARRADER = ConciseLock.of(NetUtil::getLocalAddress);

    private static boolean isLinuxPlatform = false;
    private static boolean isWindowsPlatform = false;

    static {
        if (OS_NAME != null && OS_NAME.toLowerCase().contains("linux")) {
            isLinuxPlatform = true;
        }

        if (OS_NAME != null && OS_NAME.toLowerCase().contains("windows")) {
            isWindowsPlatform = true;
        }
    }

    public static boolean isWindowsPlatform() {
        return isWindowsPlatform;
    }


    public static Selector openSelector() throws IOException {
        Selector result = null;
        if (isLinuxPlatform()) {
            try {
                Class<?> providerClazz = Class.forName("sun.nio.ch.EPollSelectorProvider");
                final Method method = providerClazz.getMethod("provider");
                final SelectorProvider selectorProvider = (SelectorProvider) method.invoke(null);
                if (selectorProvider != null) {
                    result = selectorProvider.openSelector();
                }
            } catch (final Exception e) {
                // ignore
            }
        }
        if (result == null) {
            result = Selector.open();
        }
        return result;
    }

    public static boolean isLinuxPlatform() {
        return isLinuxPlatform;
    }

    public static String getLocalAddressByCache() {
        return LOAD_ARRADER.get();
    }

    public static String getLocalAddress() {
        try {
            // Traversal Network interface to get the first non-loopback and non-private address
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4Result = new ArrayList<>();
            ArrayList<String> ipv6Result = new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = enumeration.nextElement();
                final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(normalizeHostAddress(address));
                        } else {
                            ipv4Result.add(normalizeHostAddress(address));
                        }
                    }
                }
            }

            // prefer ipv4
            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0") || ip.startsWith("192.168")) {
                        continue;
                    }

                    return ip;
                }

                return ipv4Result.get(ipv4Result.size() - 1);
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            }
            //If failed to find,fall back to localhost
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (Exception e) {
            //log.error("Failed to obtain local address", e);
        }

        return "";
    }

    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

    public static SocketAddress string2SocketAddress(final String addr) {
        String[] s = addr.split(":");
        return new InetSocketAddress(s[0], Integer.parseInt(s[1]));
    }

    public static String socketAddress2String(final SocketAddress addr) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        return inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort();
    }

    public static SocketChannel connect(SocketAddress remote) {
        return connect(remote, 1000 * 5);
    }

    public static SocketChannel connect(SocketAddress remote, final int timeoutMillis) {
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(true);
            sc.socket().setSoLinger(false, -1);
            sc.socket().setTcpNoDelay(true);
            sc.socket().setReceiveBufferSize(1024 * 64);
            sc.socket().setSendBufferSize(1024 * 64);
            sc.socket().connect(remote, timeoutMillis);
            sc.configureBlocking(false);
            return sc;
        } catch (Exception e) {
            String message = null;
            if (sc != null) {
                try {
                    sc.close();
                } catch (IOException e1) {
                    message = e1.getMessage();
                }
            }
            if (message == null) {
                message = e.getMessage();
            } else {
                message = message + " " + e.getMessage();
            }
            throw new RuntimeException(message, e);
        }
    }

}
