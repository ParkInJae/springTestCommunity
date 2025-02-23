package com.springCommunity.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	// endPoint > 하나의 긴 통신선이 있다고 가정하면 양 끝 지점을 endPoint라고 함  
        registry.addHandler(new ChatWebSocketHandler(), "/chat") //end 포인트를 /chat으로 설정함 
                .setAllowedOrigins("*");
    }
}