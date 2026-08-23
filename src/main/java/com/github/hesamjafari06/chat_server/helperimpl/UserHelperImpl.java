package com.github.hesamjafari06.chat_server.helperimpl;

import com.github.hesamjafari06.chat_server.entity.UserEntity;
import com.github.hesamjafari06.chat_server.exception.UserNotFoundException;
import com.github.hesamjafari06.chat_server.helper.UserHelper;
import com.github.hesamjafari06.chat_server.repository.UserRepository;
import com.github.hesamjafari06.chat_server.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHelperImpl implements UserHelper {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUser().getId();

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }
}
