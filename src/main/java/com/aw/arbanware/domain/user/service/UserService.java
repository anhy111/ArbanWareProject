package com.aw.arbanware.domain.user.service;


import com.aw.arbanware.domain.user.controller.LoginDto;
import com.aw.arbanware.domain.user.entity.User;
import com.aw.arbanware.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId);
    }

}
