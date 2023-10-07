package com.aw.arbanware.global.config;

import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class ArbanwareConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 로그인한 사람의 id가 리턴되도록 변경하기
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof AnonymousAuthenticationToken) {
                    return Optional.empty();
                }
                SecurityUser user = (SecurityUser)authentication.getPrincipal();
                return Optional.of(user.getId().toString());
            }
        };
    }
}
