package com.aw.arbanware.domain.user.service;


import com.aw.arbanware.domain.user.entity.User;
import com.aw.arbanware.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository<User> userRepository;

    public Optional<User> findUserByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId);
    }

}
