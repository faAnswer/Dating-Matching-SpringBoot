package org.tecky.nohorny.livechat.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.tecky.nohorny.livechat.interceptor.WebSocketInterceptor;
import org.tecky.nohorny.livechat.services.LiveChatWebSocketHandler;

@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    LiveChatWebSocketHandler liveChatWebSocketHandler;

    @Autowired
    WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(liveChatWebSocketHandler, "/ws")
                .setAllowedOrigins("*")
                .addInterceptors(webSocketInterceptor);
    }
}
