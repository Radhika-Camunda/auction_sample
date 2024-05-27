package com.sample.auctions.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {

    private String accessToken;
    private String refreshToken;

}
