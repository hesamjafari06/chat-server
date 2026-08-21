package com.github.hesamjafari06.chat_server.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(
            Message<?> message,
            MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(
                        message,
                        StompHeaderAccessor.class
                );

        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();

        System.out.println("COMMAND = " + command);

        if (StompCommand.CONNECT.equals(command)) {

            String authorization =
                    accessor.getFirstNativeHeader("Authorization");

            System.out.println("AUTH HEADER = " + authorization);

            if (authorization == null ||
                    !authorization.startsWith("Bearer ")) {

                throw new IllegalArgumentException(
                        "Missing Authorization"
                );
            }

            try {

                String token = authorization.substring(7);

                String username =
                        jwtService.extractUsername(token);

                System.out.println("USERNAME = " + username);

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                if (!jwtService.isTokenValid(token, userDetails)) {
                    throw new IllegalArgumentException(
                            "Invalid JWT"
                    );
                }

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                accessor.setUser(authentication);

                System.out.println(
                        "WEBSOCKET AUTHENTICATED = " + username
                );

            } catch (Exception e) {

                System.out.println(
                        "WEBSOCKET AUTH FAILED: " + e.getMessage()
                );

                throw new IllegalArgumentException(
                        "Invalid JWT",
                        e
                );
            }
        }

        System.out.println(
                "PRINCIPAL = " + accessor.getUser()
        );

        return message;
    }
}