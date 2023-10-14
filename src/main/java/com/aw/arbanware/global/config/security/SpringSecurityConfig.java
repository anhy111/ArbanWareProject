package com.aw.arbanware.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.cors().disable();
        http.csrf().disable();
        http.authorizeHttpRequests(request ->
                request.anyRequest().permitAll()
        ).formLogin(login ->
                login.loginPage("/login")
                        .usernameParameter("loginId")
                        .passwordParameter("loginPassword")
                        .failureHandler(failureHandler())
                        .successHandler(successHandler())
                        .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.exceptionHandling()
                .accessDeniedPage("/accessDenied");
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // 시큐리티 제외시킬 페이지의 url. 문자열로 계속 추가하면댐
//        // /** -> /하위 모든 url을 제외시킴.
//        // /* -> 해당 디렉토리의 파일들(위와다름, 위는 하위 디렉토리의 파일들 포함)
//        return (web) -> web.ignoring().antMatchers("/", "/css/**", "/img/**", "/js/**", "/lib/**",
//                "/mail/**","/scss/**");
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler("/");
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new LoginFailureHandler("/login");
    }

}
