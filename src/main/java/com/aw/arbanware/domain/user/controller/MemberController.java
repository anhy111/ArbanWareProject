package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String register(Model model) {
        model.addAttribute("member", new Member());
        return "page/member/register";
    }

    @PostMapping("/member")
    public String  registerPost(@ModelAttribute("member") Member member) {
        memberService.save(member);
        return "redirect:/";
    }
}
