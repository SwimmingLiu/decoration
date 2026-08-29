package com.lamp.decoration.foundation.network.hmr;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class FileWatcherService {

    private static final String WATCH_DIR = "./src";
    private static final long DEBOUNCE_MS = 100;

    private final Set<String> pendingUpdates = ConcurrentHashMap.newKeySet();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private volatile long lastChangeTime = 0;

    public void start() {
        Thread watcherThread = new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                Path path = Paths.get(WATCH_DIR).toAbsolutePath();
                path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

                System.out.println("[Netty-HMR] Watching: " + path);

                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path fileName = (Path) event.context();
                        if (isSourceFile(fileName.toString())) {
                            handleFileChange("/" + fileName.toString().replace("\\", "/"), event.kind());
                        }
                    }
                    key.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        watcherThread.setDaemon(true);
        watcherThread.start();
    }

    private boolean isSourceFile(String name) {
        return name.endsWith(".vue") || name.endsWith(".js") || name.endsWith(".ts")
               || name.endsWith(".css") || name.endsWith(".jsx");
    }

    private void handleFileChange(String path, WatchEvent.Kind<?> kind) {
        pendingUpdates.add(path);
        lastChangeTime = System.currentTimeMillis();

        // 提交防抖任务
        scheduler.schedule(() -> {
            if (System.currentTimeMillis() - lastChangeTime >= DEBOUNCE_MS) {
                flushUpdates();
            }
        }, DEBOUNCE_MS, TimeUnit.MILLISECONDS);
    }

    private void flushUpdates() {
        if (pendingUpdates.isEmpty()) {
            return;
        }

        Set<String> updatesToSend = ConcurrentHashMap.newKeySet();
        updatesToSend.addAll(pendingUpdates);
        pendingUpdates.clear();

        // 简单策略：如果有删除操作，或包含非组件 JS 文件，保守发送 full-reload
        // 这里为了演示协议，统一发送 update，实际项目需更复杂的依赖判断
        String message = HmrProtocolBuilder.buildUpdateMessage(updatesToSend);

        broadcast(message);
        System.out.println("[Netty-HMR] Broadcasted update for: " + updatesToSend);
    }

    private void broadcast(String message) {
        TextWebSocketFrame frame = new TextWebSocketFrame(message);
        for (ChannelHandlerContext ctx : HmrWebSocketHandler.clients.values()) {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(frame.retain()); // retain 因为是多播
            }
        }
    }
}
