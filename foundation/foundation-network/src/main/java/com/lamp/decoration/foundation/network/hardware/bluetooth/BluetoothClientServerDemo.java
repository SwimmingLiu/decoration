package com.lamp.decoration.foundation.network.hardware.bluetooth;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 * Java蓝牙通信演示：包含服务端（等待连接）和客户端（发起连接）逻辑。
 * 注意：实际运行需要蓝牙硬件支持，且通常需要在不同线程或不同设备上运行服务端和客户端。
 * 为了演示方便，本类将两者逻辑整合，但实际使用时请分开部署。
 */
public class BluetoothClientServerDemo {

    // 定义一个唯一的UUID用于服务识别
    private static final String SERVICE_UUID = "0000110100001000800000805F9B34FB"; // SPP UUID

    public static void main(String[] args) {
        System.out.println("蓝牙通信演示启动...");

        // 在实际场景中，服务端和客户端通常运行在不同的JVM或设备上。
        // 这里我们展示两个独立的方法，你可以选择运行其中一个，或者在两个终端分别运行。

        // 选项1: 启动服务端 (等待连接)
        // startServer();

        // 选项2: 启动客户端 (搜索并连接) - 需要先知道服务端的地址或URL
        // startClient();

        System.out.println("请根据需求取消注释 startServer() 或 startClient() 方法，并确保蓝牙已开启。");
    }

    /**
     * 蓝牙服务端逻辑
     * 1. 获取本地设备
     * 2. 注册服务记录
     * 3. 等待客户端连接
     * 4. 接收数据并响应
     */
    public static void startServer() {
        StreamConnectionNotifier streamConnNotifier = null;
        StreamConnection streamConnection = null;

        try {
            // 1. 获取本地蓝牙设备
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            System.out.println("本地蓝牙地址: " + localDevice.getBluetoothAddress());
            System.out.println("本地蓝牙名称: " + localDevice.getFriendlyName());

            // 2. 创建服务URL
            // btspp://localhost:<UUID>;name=<ServiceName>
            String url = "btspp://localhost:" + SERVICE_UUID + ";name=JavaBluetoothService";

            // 3. 打开连接通知器 (注册服务)
            streamConnNotifier = (StreamConnectionNotifier) Connector.open(url);
            System.out.println("服务端已启动，等待客户端连接... URL: " + url);

            // 4. 等待客户端连接 (阻塞方法)
            streamConnection = streamConnNotifier.acceptAndOpen();
            System.out.println("客户端已连接!");

            // 5. 处理数据交互
            handleCommunication(streamConnection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源
            try {
                if (streamConnection != null) streamConnection.close();
                if (streamConnNotifier != null) streamConnNotifier.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 蓝牙客户端逻辑
     * 1. 发现设备
     * 2. 搜索服务
     * 3. 建立连接
     * 4. 发送请求并接收响应
     */
    public static void startClient() {
        StreamConnection streamConnection = null;
        try {
            // 1. 获取本地设备
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();

            System.out.println("开始搜索蓝牙设备...");

            // 2. 搜索设备 (同步方式简化演示，实际建议使用异步DiscoveryListener)
            // 注意：startInquiry是异步的，这里为了代码简洁使用预已知设备或模拟连接
            // 在实际应用中，你需要实现 DiscoveryListener 接口来获取 RemoteDevice

            // 假设我们已经通过某种方式知道了服务端的蓝牙地址和服务URL
            // 例如：String serverAddress = "001122334455";
            // String url = "btspp://" + serverAddress + ":1;authenticate=false;encrypt=false;master=false";

            // 由于无法在纯代码演示中动态获取远程地址而不使用复杂的回调结构，
            // 这里展示如何连接到已知的URL。
            // 在实际测试中，请先运行服务端，获取其打印的URL或地址，然后填入此处。

            String serverUrl = "btspp://001122334455:1"; // 请替换为实际服务端的蓝牙地址

            System.out.println("正在连接服务端: " + serverUrl);
            streamConnection = (StreamConnection) Connector.open(serverUrl);
            System.out.println("连接成功!");

            // 3. 发送请求并接收响应
            handleCommunication(streamConnection);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (streamConnection != null) streamConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理具体的数据读写逻辑
     * @param connection 蓝牙连接对象
     */
    private static void handleCommunication(StreamConnection connection) throws IOException {
        InputStream inputStream = connection.openInputStream();
        OutputStream outputStream = connection.openOutputStream();

        // 简单的请求-响应协议示例
        // 如果是服务端，先读后写；如果是客户端，先写后读。
        // 这里为了通用性，我们做一个简单的回声测试或命令交互。

        // 判断当前角色可以通过检查流的状态或传入标志位，这里简化为手动控制

        // 示例：发送一条消息
        String message = "Hello Bluetooth!";
        byte[] data = message.getBytes();
        outputStream.write(data);
        outputStream.flush();
        System.out.println("已发送: " + message);

        // 示例：接收一条消息
        byte[] buffer = new byte[128];
        int bytesRead = inputStream.read(buffer);
        if (bytesRead > 0) {
            String response = new String(buffer, 0, bytesRead);
            System.out.println("收到响应: " + response);
        }

        // 关闭流
        inputStream.close();
        outputStream.close();
    }

    /**
     * 辅助类：用于异步设备发现的监听器实现（参考用）
     */
    static class SimpleDiscoveryListener implements DiscoveryListener {
        private Vector<RemoteDevice> devices = new Vector<>();

        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            try {
                System.out.println("发现设备: " + btDevice.getFriendlyName(false) + " [" + btDevice.getBluetoothAddress() + "]");
                devices.addElement(btDevice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void inquiryCompleted(int discType) {
            System.out.println("设备搜索完成。发现设备数量: " + devices.size());
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
            System.out.println("服务搜索完成。响应码: " + respCode);
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            for (ServiceRecord record : servRecord) {
                String url = record.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                System.out.println("发现服务 URL: " + url);
            }
        }

        public Vector<RemoteDevice> getDevices() {
            return devices;
        }
    }
}

