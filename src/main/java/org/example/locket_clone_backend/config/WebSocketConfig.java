package org.example.locket_clone_backend.config;

import java.util.List;
import java.util.NoSuchElementException;

import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements
    WebSocketMessageBrokerConfigurer {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/user");
    config.setApplicationDestinationPrefixes("/app");
    config.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/app-ws");
  }

  @Override
  public void configureClientOutboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          String authHeader = accessor.getFirstNativeHeader("Authorizaton");
          String token = authHeader.substring(7);

          try {
            Long id = jwtUtil.verifyToken(token);
            System.out.println(id);
            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              final UserEntity userEntity = userRepository.findById(id).orElseThrow();
              String email = userEntity.getEmail();
              UserDetails userDetails = userDetailsService.loadUserByUsername(email);
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities());

              accessor.setUser(authToken);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        return message;
      }
    });
  }

  // @Override
  // public boolean configureMessageConverters(List<MessageConverter>
  // messageConverters) {
  // DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
  // resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
  // MappingJackson2MessageConverter converter = new
  // MappingJackson2MessageConverter();
  // converter.setObjectMapper(new ObjectMapper());
  // converter.setContentTypeResolver(resolver);
  // messageConverters.add(converter);
  // return false;
  // }

}
