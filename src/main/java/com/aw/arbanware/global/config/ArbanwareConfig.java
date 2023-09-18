package com.aw.arbanware.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class ArbanwareConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // 지금은 UUID지만 추후에 로그인기능이 구현된 후에
        // 로그인한 사람의 id가 리턴되도록 변경하기
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of(UUID.randomUUID().toString());
            }
        };
    }
}
