package com.paulo.ticTacToe.config;

import com.paulo.ticTacToe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.messaging.simp.SimpMessageType.CONNECT_ACK;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private Logger logger = Logger.getLogger(WebSocketConfig.class.getName());

    @Autowired
    private GameService gameService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ticTacToe")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(new InboundChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration
                .interceptors(new OutboundChannelInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic", "/queue");
    }

    private class InboundChannelInterceptor implements ChannelInterceptor {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            final StompCommand command = (StompCommand) message.getHeaders().get("stompCommand");
            final String sessionId = (String) message.getHeaders().get("simpSessionId");
            if (command == StompCommand.CONNECT) {
                logger.log(
                        Level.INFO,
                        String.format("Client with sessionId %s just connected to the lobby",
                                sessionId));
            } else if (command == StompCommand.DISCONNECT) {
                logger.log(
                        Level.INFO,
                        String.format("%s just disconnected from the lobby", sessionId));
                gameService.handlePlayerDisconnect(sessionId);
            }
            return message;

        }
    }

    private class OutboundChannelInterceptor implements ChannelInterceptor {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            final String sessionId = (String) message.getHeaders().get("simpSessionId");
            final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
            final SimpMessageType messageType = headerAccessor.getMessageType();
            if (messageType == CONNECT_ACK) {
                final StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECTED);
                accessor.setSessionId(sessionId);
                // add custom headers
                accessor.addNativeHeader("name", sessionId);
                final Message<?> newMessage = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
                return newMessage;
            }
            return message;
        }
    }
}
