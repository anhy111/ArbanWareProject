package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.infra.email.Email;
import com.aw.arbanware.infra.email.EmailService;
import com.aw.arbanware.infra.email.EmailStatus;
import com.aw.arbanware.infra.email.Recipient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String register(Model model) {
        model.addAttribute("member", new RegisterMemberForm());
        return "page/member/register";
    }

    @PostMapping("/members/new")
    public String  registerPost(@ModelAttribute("member") RegisterMemberForm member) {
        return "redirect:/";
    }

    @PostMapping("/members/email")
    @ResponseBody
    public String email(@RequestBody Recipient recipient, HttpSession session){
        final String randomNum = createRandomNum();
        final EmailStatus emailStatus = EmailService.sendMail(new Email(recipient), randomNum);
        log.info("recipient = {}", recipient);
        if (emailStatus == EmailStatus.FAIL) {
            return "fail";
        }
        session.setAttribute("emailAuth", randomNum);

        return "success";
    }

    @PostMapping("/members/emailCheck")
    @ResponseBody
    public ResponseEntity emailCheck(@RequestParam("emailAuthInput") String emailAuthInput
                                                                , HttpSession session) {
        final Object obj = session.getAttribute("emailAuth");
        if (obj == null) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.NOT_REQ_EMAIL)
                    , HttpStatus.OK);
        }

        final String emailAuth = (String) obj;
        log.info("session auth={}", emailAuth);
        log.info("user.auth={}", emailAuthInput);

        if (!emailAuth.equals(emailAuthInput)) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.AUTH_FAIL)
                    , HttpStatus.OK);
        }
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.EMAIL_AUTH_SUCCESS)
                , HttpStatus.OK);
    }

    private static String createRandomNum() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return String.valueOf(generator.nextInt(1000000) % 1000000);
    }
}
