package com.lamp.decoration.foundation.network.hmr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class HmrWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 存储所有连接的客户端 Channel
    public static final Map<String, ChannelHandlerContext> clients = new ConcurrentHashMap<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 只有当握手完成后，channel 才会被添加到 pipeline 的后续处理中
        // 这里我们依赖 WebSocketServerProtocolHandler 完成握手
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        // 可选：处理前端发来的心跳 {"type":"ping"}
        // System.out.println("Received: " + msg.text());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ctx.channel().id().asShortText();
        clients.remove(clientId);
        System.out.println("[Netty-HMR] Client disconnected: " + clientId);
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 监听握手完成事件
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete =
                (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String clientId = ctx.channel().id().asShortText();
            clients.put(clientId, ctx);
            System.out.println("[Netty-HMR] Client connected: " + clientId);
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
