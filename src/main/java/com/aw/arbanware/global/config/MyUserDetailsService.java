package com.aw.arbanware.global.config;

import com.aw.arbanware.domain.authorization.Authorization;
import com.aw.arbanware.domain.user.entity.User;
import com.aw.arbanware.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
        final Optional<User> findUser = userService.findUserByLoginId(loginId);
        final User user = findUser.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));

        final List<GrantedAuthority> authorities = user.getAuthorization()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getLoginPassword())
                .authorities(authorities)
                .build();
    }
}
