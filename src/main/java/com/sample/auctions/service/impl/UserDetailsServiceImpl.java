package com.sample.auctions.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sample.auctions.exceptions.NotFoundException;
import com.sample.auctions.model.user.User;
import com.sample.auctions.repository.UserRepository;
import com.sample.auctions.security.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        "User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
