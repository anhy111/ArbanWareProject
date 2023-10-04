package com.aw.arbanware.global.config.security;

import com.aw.arbanware.domain.user.entity.User;
import com.aw.arbanware.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String loginId) throws UsernameNotFoundException {
        final Optional<User> findUser = userService.findUserByLoginId(loginId);
        User user = findUser.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));

        if (user instanceof HibernateProxy) {
            user = (User)Hibernate.unproxy(user);
        }

        final List<GrantedAuthority> authorities = user.getAuthorization()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                .collect(Collectors.toList());

        return new SecurityUser(user, authorities);
    }
}
