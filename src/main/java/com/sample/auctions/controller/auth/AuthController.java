package com.sample.auctions.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.auctions.dto.auth.JwtResponse;
import com.sample.auctions.dto.auth.LoginRequest;
import com.sample.auctions.dto.auth.SingUpRequest;
import com.sample.auctions.dto.auth.TokenRefreshRequest;
import com.sample.auctions.dto.auth.TokenRefreshResponse;

import com.sample.auctions.exceptions.DuplicatedLoginException;
import com.sample.auctions.exceptions.NotFoundException;
import com.sample.auctions.model.RefreshToken;
import com.sample.auctions.model.user.RoleEnum;
import com.sample.auctions.model.user.User;
import com.sample.auctions.security.JwtUtils;
import com.sample.auctions.security.UserDetailsImpl;
import com.sample.auctions.service.impl.UserDetailsServiceImpl;
import com.sample.auctions.service.interfaces.RefreshTokenService;
import com.sample.auctions.service.interfaces.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final AuthenticationManager authenticationManager;

    final RefreshTokenService refreshTokenService;

    final UserDetailsServiceImpl userDetailsService;

    final UserService userService;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    @PostMapping("/signin")
    @Operation(summary = "Sign in to the application")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()).get(0);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken()));
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh token")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new NotFoundException("Refresh token is not in database!"));

    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up to the application")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SingUpRequest singupRequest) {

        if (userService.existsByUsername(singupRequest.getUsername())) {
            throw new DuplicatedLoginException("Error: Username is already taken!");
        }

		

        User user = new User(singupRequest.getEmail(),
                encoder.encode(singupRequest.getPassword()),
                singupRequest.getUsername()
        );

        user.setRoleName(RoleEnum.ROLE_USER);
        userService.createUser(user);
        return ResponseEntity.status(201).body("User registered successfully!");
    }
}
