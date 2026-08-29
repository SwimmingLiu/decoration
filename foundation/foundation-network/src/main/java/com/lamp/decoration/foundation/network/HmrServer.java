package com.lamp.decoration.foundation.network;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import com.alibaba.fastjson2.JSON;

/**
 * Netty WebSocket HMR Server 完整实现 依赖: netty-all, gson
 */
public class HmrServer {

    // 4. 主启动类
    public static void main(String[] args) throws InterruptedException {
        int port = 8085;
        String watchDir = "./dist"; // 修改为你需要监听的构建输出目录

        // 启动文件监听
        new FileWatcherService().startWatching(watchDir);

        // 启动 Netty 服务器
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpServerCodec());
                        p.addLast(new HttpObjectAggregator(65536));
                        // WebSocket 路径: ws://localhost:8085/hmr-ws
                        p.addLast(new WebSocketServerProtocolHandler("/hmr-ws"));
                        p.addLast(new HmrWebSocketHandler());
                    }
                });

            ChannelFuture f = b.bind(port).sync();
            System.out.println("HMR Server 已启动，端口: " + port);
            System.out.println("WebSocket 地址: ws://localhost:" + port + "/hmr-ws");

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 1. 消息实体
    public static class HmrMessage {

        private String type;
        private String path;
        private long timestamp;

        public HmrMessage(String type, String path) {
            this.type = type;
            this.path = path;
            this.timestamp = System.currentTimeMillis();
        }
        // Getter/Setter 省略，Gson 可直接访问字段
    }

    // 2. WebSocket Handler
    public static class HmrWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        private static final ChannelGroup clients = new DefaultChannelGroup(new DefaultEventLoop());

        // 广播更新
        public static void broadcastUpdate(String filePath) {
            HmrMessage msg = new HmrMessage("update", filePath);
            String json = JSON.toJSONString(msg);
            System.out.println("广播更新: " + json);
            clients.writeAndFlush(new TextWebSocketFrame(json));
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            clients.add(ctx.channel());
            System.out.println("客户端连接: " + ctx.channel().remoteAddress());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            clients.remove(ctx.channel());
            System.out.println("客户端断开: " + ctx.channel().remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
            // 可选：处理前端心跳
            System.out.println("收到消息: " + msg.text());
        }
    }

    // 3. 文件监听服务
    public static class FileWatcherService {

        public void startWatching(String watchDir) {
            new Thread(() -> {
                try {
                    WatchService watchService = FileSystems.getDefault().newWatchService();
                    Path path = Paths.get(watchDir);
                    path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                    System.out.println("开始监听目录: " + watchDir);

                    while (true) {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                Path changedFile = (Path) event.context();
                                String fileName = changedFile.toString();

                                // 简单过滤，只处理 js/css 文件
                                if (fileName.endsWith(".js") || fileName.endsWith(".css")) {
                                    // 去抖：实际生产建议用 ScheduledExecutorService
                                    TimeUnit.MILLISECONDS.sleep(300);
                                    HmrWebSocketHandler.broadcastUpdate(fileName);
                                }
                            }
                        }
                        key.reset();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
