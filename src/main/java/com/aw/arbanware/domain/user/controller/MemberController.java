package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.infra.email.Email;
import com.aw.arbanware.infra.email.EmailService;
import com.aw.arbanware.infra.email.Recipient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/members/email")
    @ResponseBody
    public String email(@RequestBody Recipient recipient){
        EmailService.sendMail(new Email(recipient));
        log.info("recipient = {}", recipient);
        return "success";
    }
}
