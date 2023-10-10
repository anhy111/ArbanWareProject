package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.user.service.UserService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String longinPage(HttpServletRequest request) {
        final String referer = request.getHeader("Referer");
        request.getSession().setAttribute("Referer", referer);
        return "page/login/login";
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    public String info(@AuthenticationPrincipal SecurityUser securityUser) {
        log.info("user.name = {}", securityUser.getName());
        log.info("user.id = {}", securityUser.getId());
        log.info("user.loginId = {}", securityUser.getUsername());
        log.info("user.loginPassword = {}", securityUser.getPassword());
        log.info("user.authorities = {}", securityUser.getAuthorities());
        return "redirect:/";
    }

    @GetMapping("/ww")
    @PreAuthorize("hasAnyRole('admin')")
    public String ww(@AuthenticationPrincipal SecurityUser securityUser) {
        log.info("user.name = {}", securityUser.getName());
        log.info("user.id = {}", securityUser.getId());
        log.info("user.loginId = {}", securityUser.getUsername());
        log.info("user.loginPassword = {}", securityUser.getPassword());
        log.info("user.authorities = {}", securityUser.getAuthorities());
        return "redirect:/";
    }

    @GetMapping("/accessDenied")
    public void accessDenied(HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('처리할 수 없는 요청입니다.');history.go(-1);</script>");
            w.flush();
            w.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
