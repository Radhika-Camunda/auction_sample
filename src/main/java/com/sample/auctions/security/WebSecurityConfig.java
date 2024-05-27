package com.sample.auctions.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sample.auctions.service.impl.UserDetailsServiceImpl;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    final UserDetailsServiceImpl userDetailsService;

    final AuthEntryPoint authEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/users/change-password")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasRole("SELLER")

                        .requestMatchers(HttpMethod.GET, "/auctions")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/auctions/buy-now")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.POST, "/auctions/bidding")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.PUT, "/auctions/{auctionId}")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.DELETE, "/auctions/{auctionId}")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.GET, "/auctions/{auctionId}")
                        .hasRole("SELLER")

                        .requestMatchers(HttpMethod.POST, "/auctions/{auctionId}/bids")
                        .hasAnyRole("SELLER", "BUYER")
                        .requestMatchers(HttpMethod.DELETE, "/{auctionId}/bids/{bidId}")
                        .hasAnyRole("SELLER", "BUYER")

                        .requestMatchers(HttpMethod.GET, "/{auctionId}/bids")
                        .hasRole("SELLER")

                        .anyRequest().authenticated())
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}