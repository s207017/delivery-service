package org.delivery.api.account.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public AccountMeResponse getCurrentAccountProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        
        return AccountMeResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .registeredAt(user.getRegisteredAt())
                .build();
    }
}


