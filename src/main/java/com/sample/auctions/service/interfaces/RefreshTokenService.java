package com.sample.auctions.service.interfaces;

import java.util.Optional;

import com.sample.auctions.model.RefreshToken;

public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    void deleteByUserId(Long userId);
}
