package com.example.backend.config;

import com.example.backend.service.NotificationDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class NotificationConfig implements WebSocketConfigurer {

    @Autowired
    private NotificationDistributor notificationDistributor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.notificationDistributor, "/ws/notifications")
                .setAllowedOriginPatterns("http://localhost:*",
                        "https://ewa-frontend-app.onrender:*",
//                        getHostIPAddressPattern(),
                        "http://*.hva.nl:*"
                );
    }
}
