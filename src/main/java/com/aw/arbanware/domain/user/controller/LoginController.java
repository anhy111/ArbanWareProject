package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.user.service.UserService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String longinPage(HttpServletRequest request) {
        log.info("encodePassword = {}", passwordEncoder.encode("1234"));
        final String referer = request.getHeader("Referer");
        log.info("referer = {}", referer);
        if (!referer.contains("members/new")) {
            request.getSession().setAttribute("Referer", referer);
        }
        return "page/login/login";
    }

    @GetMapping("/searchPassword")
    public String searchPasswordPage() {

        return "page/login/search_password";
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
