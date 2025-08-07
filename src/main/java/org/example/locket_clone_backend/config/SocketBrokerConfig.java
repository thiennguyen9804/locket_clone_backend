package org.example.locket_clone_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements
    WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/secured/user/queue/specific-user");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/secured/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat-websocket");
  }

  // @Bean
  // public SimpMessagingTemplate messagingTemplate(MessageBrokerRegistry
  // registry) {
  // return this.messagingTemplate(registry);
  // }

}
