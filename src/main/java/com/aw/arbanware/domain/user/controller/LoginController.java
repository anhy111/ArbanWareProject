package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.repository.ChangePassword;
import com.aw.arbanware.domain.user.repository.SearchPassword;
import com.aw.arbanware.domain.user.repository.SearchPasswordType;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.domain.user.service.UserService;
import com.aw.arbanware.global.config.security.LoginFailureMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String longinPage(HttpServletRequest request,
                             Model model,
                             @RequestParam(value = "error", required = false) boolean error,
                             @RequestParam(value = "message", required = false) LoginFailureMessage errorMessage) {
        final String referer = request.getHeader("Referer");
        log.info("referer = {}", referer);
        model.addAttribute("error", error);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage.getMessage());
        }
        if (referer != null && (!referer.contains("members/new") && !referer.contains("/login"))) {
            request.getSession().setAttribute("Referer", referer);
        }
        return "page/login/login";
    }

    @GetMapping("/searchPassword")
    public String searchPasswordPage(Model model) {
        model.addAttribute("form",new SearchPassword());
        return "page/login/search_password";
    }

    @PostMapping("/searchPassword")
    public String searchPassword(@ModelAttribute SearchPassword searchPassword,
                                 Model model,
                                 HttpSession session) {
        log.info("form = {}", searchPassword);
        final Optional<Member> findMember;
        if (searchPassword.getSearchPasswordType() == SearchPasswordType.EMAIL) {
            findMember = memberService.findSearchPasswordEmailMember(searchPassword);
        } else {
            findMember = memberService.findSearchPasswordPhoneMember(searchPassword);
        }

        if (findMember.isEmpty()) {
            model.addAttribute("form", searchPassword);
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "입력된 정보로 가입된 회원은 존재하지 않습니다.");
            return "page/login/search_password";
        }
        model.addAttribute("member", findMember.get());
        model.addAttribute("type", searchPassword.getSearchPasswordType());
        session.setAttribute("smsAuth", "121212");
        session.setAttribute("emailAuth", "121212");
        return "page/login/change_password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute ChangePassword changePassword) {
        log.info("changePassword = {}", changePassword);
        memberService.changePassword(changePassword);
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
