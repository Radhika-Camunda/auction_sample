package com.sample.auctions.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sample.auctions.model.RefreshToken;
import com.sample.auctions.repository.RefreshTokenRepository;
import com.sample.auctions.repository.UserRepository;
import com.sample.auctions.service.interfaces.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Value("${auctions.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with id " + userId + " not found")
        ));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new IllegalArgumentException(token.getToken() +
                    " refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserUserId(userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with id " + userId + " not found")
        ).getUserId());
    }
}
