package com.example.demo.config;


import com.example.demo.controllers.QueueWebSocket;
import com.example.demo.controllers.WebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketController webSocketHandler;
    private final QueueWebSocket queueWebSocket;
    @Autowired
    public WebSocketConfiguration(WebSocketController webSocketHandler, QueueWebSocket queueWebSocket) {
        this.webSocketHandler = webSocketHandler;
        this.queueWebSocket = queueWebSocket;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws")
                .setAllowedOrigins("http://localhost:5173") // Your React app port
                .addInterceptors(new HttpSessionHandshakeInterceptor());
        registry.addHandler(queueWebSocket,"/qs")
                .setAllowedOrigins("http://localhost:5173")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

}