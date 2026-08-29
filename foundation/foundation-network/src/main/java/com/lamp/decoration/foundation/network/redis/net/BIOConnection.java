package com.lamp.decoration.foundation.network.redis.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.net.ssl.SSLSocket;

import com.lamp.decoration.foundation.network.redis.utils.LedisInputStream;


public class BIOConnection extends AbstractConnection {

    private Socket socket;
    private OutputStream outputStream;
    private LedisInputStream inputStream;

    private boolean isConnection = false;

    public BIOConnection(NetConfigure configure) {
        super(configure);
        connect();
    }

    @Override
    public boolean isConnect() {
        return isConnection;
    }

    @Override
    public void connect() {
        try {
            socket = new Socket();
            socket.setReuseAddress(true);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            socket.setSoLinger(true, 0);
            socket.connect(new InetSocketAddress(configure.getHost(), configure.getPost()), 2000);
            socket.setSoTimeout(60000);

            if (configure.isSsl()) {
                socket = configure.getSllSocketFactory().createSocket(socket, configure.getHost(), configure.getPost(), true);
                if (null != configure.getSslParameters()) {
                    ((SSLSocket) socket).setSSLParameters(configure.getSslParameters());
                }
                if ((null != configure.getHostnameVerifier()) && (!configure.getHostnameVerifier()
                    .verify(configure.getHost(), ((SSLSocket) socket).getSession()))) {
                    String message = String.format("The connection to '%s' failed ssl/tls hostname verification.",
                        configure.getHost());
                    throw new RuntimeException(message);
                }
            }
            outputStream = socket.getOutputStream();
            //outputStream = new StringOutputStream(new ExceptionOutputStream(outputStream));
            outputStream = new StringOutputStream(outputStream);
            inputStream = new LedisInputStream(socket.getInputStream());
            isConnection = true;
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public LedisInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }


    public class ExceptionInputStream extends InputStream {

        private InputStream inputStream;

        public ExceptionInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            try {
                int i = inputStream.read();
                return i;
            } catch (IOException e) {
                BIOConnection.this.isConnection = false;
                throw e;
            }
        }
    }

    private class ExceptionOutputStream extends OutputStream {

        private OutputStream outputStream;

        private ByteBuffer buffer = ByteBuffer.allocate(1024 << 4);

        private byte[] by = buffer.array();


        public ExceptionOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
        }


        @Override
        public void write(int b) throws IOException {
            write0(1);
            buffer.put((byte) b);
        }

        @Override
        public void write(byte[] by) throws IOException {
            write0(by.length);
            if (by.length >= buffer.capacity()) {
                write0(by);
            } else {
                buffer.put(by);
            }

        }

        private void write0(byte[] length) throws IOException {
            try {
                outputStream.write(by, 0, buffer.position());
            } catch (Exception e) {
                BIOConnection.this.isConnection = false;
                throw e;
            }
        }

        private void write0(int length) throws IOException {
            if (buffer.limit() - buffer.position() <= length) {
                try {
                    outputStream.write(by, 0, buffer.position());
                    buffer.position(0).limit(buffer.capacity());
                } catch (Exception e) {
                    BIOConnection.this.isConnection = false;
                    throw e;
                }

            }
        }
    }

    private static class StringOutputStream extends OutputStream {

        private OutputStream outputStream;

        private ByteArrayOutputStream bais = new ByteArrayOutputStream();

        StringOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;

        }

        @Override
        public void write(int b) throws IOException {
            bais.write(b);
            outputStream.write(b);
        }


        @Override
        public void flush() throws IOException {
            byte[] b = bais.toByteArray();
            System.out.println(new String(b));
            outputStream.flush();
        }
    }


}
