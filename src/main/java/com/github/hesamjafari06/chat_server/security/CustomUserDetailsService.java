package com.github.hesamjafari06.chat_server.security;

import com.github.hesamjafari06.chat_server.mapper.UserMapper;
import com.github.hesamjafari06.chat_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toUserDetails(
                userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        );
    }
}
