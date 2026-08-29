package com.lamp.decoration.foundation.network.hmr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *  解决
 */
public class NettyHmrServer {

    private static final int PORT = 24678;

    public static void main(String[] args) throws InterruptedException {
        new NettyHmrServer().start();
    }

    public void start() throws InterruptedException {
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
                        // HTTP 编解码
                        p.addLast(new HttpServerCodec());
                        // 聚合 HTTP 消息
                        p.addLast(new HttpObjectAggregator(65536));
                        // 支持大文件传输（虽 HMR 不用，但习惯加上）
                        p.addLast(new ChunkedWriteHandler());
                        // WebSocket 握手处理，路径通常为 /vite-hmr 或 /
                        p.addLast(new WebSocketServerProtocolHandler("/", null, true));
                        // 自定义业务处理
                        p.addLast(new HmrWebSocketHandler());
                    }
                });

            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("[Netty-HMR] Server started on port " + PORT);

            // 启动文件监听
            new FileWatcherService().start();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
