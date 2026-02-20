package com.example.velora.user.service;

import com.example.velora.auth.dto.RegisterRequest;
import com.example.velora.user.entity.User;
import com.example.velora.user.entity.UserRole;
import com.example.velora.user.repository.UserRepository;
import com.example.velora.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        Optional<User> optionalEmailUser = userRepository.findByEmail(registerRequest.getEmail());
        if (optionalEmailUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .createdOn(LocalDateTime.now())
                .isActive(true)
                .build();

        userRepository.save(user);
    }
}
