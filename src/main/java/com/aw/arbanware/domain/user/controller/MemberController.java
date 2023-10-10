package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.entity.User;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.domain.user.service.UserService;
import com.aw.arbanware.infra.email.Email;
import com.aw.arbanware.infra.email.EmailService;
import com.aw.arbanware.infra.email.EmailStatus;
import com.aw.arbanware.infra.email.Recipient;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final UserService userService;
    private final DefaultMessageService messageService;

    public MemberController(final MemberService memberService, final UserService userService) {
        this.memberService = memberService;
        this.userService = userService;
        messageService = NurigoApp.INSTANCE.initialize("NCSZB6C5M2PZ5XTN", "ZNBCI4SKYJGKCQ5MIHZTW1I98WTYDRQY", "https://api.coolsms.co.kr");
    }

    @GetMapping("/members/new")
    public String register(Model model, HttpSession session) {
        model.addAttribute("member", new RegisterMemberForm());
        session.setAttribute("smsAuth", "121212");
        session.setAttribute("emailAuth", "121212");
        return "page/member/register";
    }

    @PostMapping("/members/new")
    public String  registerPost(@ModelAttribute("member") RegisterMemberForm form) {
        log.info("form = {}", form);
        final Member member = RegisterMemberForm.createMember(form);
        memberService.save(member);
        return "redirect:/login";
    }


    @PostMapping("/members/duplicateIdCheck")
    @ResponseBody
    public ResponseEntity duplicateIdCheck(@RequestParam("loginId") String loginId) {
        log.info("loginId = {}", loginId);
        final Optional<User> findUser = userService.findUserByLoginId(loginId);
        if (!findUser.isEmpty()) {
            return new ResponseEntity(new DefaultResponse(StatusCode.CONFLICT, ResponseMessage.DUPLICATE_LOGIN_ID)
                    , HttpStatus.OK);
        }
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }

    @PostMapping("/members/sendSms")
    @ResponseBody
    public ResponseEntity sendSms(@RequestParam("phoneNumber") String phoneNumber, HttpSession session) {
        log.info("phoneNumber = {}", phoneNumber);
        final String randomNum = createRandomNum();
        log.info("randomNum = {}", randomNum);

        Message message = new Message();
        message.setFrom("01094366849");
        message.setTo(phoneNumber);
        final String formatStr = String.format("Arban Ware 인증번호 [%s]를 \n입력해주세요.", randomNum);
        message.setText(formatStr);

        SingleMessageSentResponse response = this.messageService.sendOne(
                new SingleMessageSendingRequest(message));
        log.info("response={}",response);
        if (!response.getStatusCode().equals("2000") && !response.getStatusCode().equals("4000")) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.REQ_SMS_FAIL)
                    , HttpStatus.OK);
        }
        session.setAttribute("smsAuth", randomNum);
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.REQ_SMS_SUCCESS)
                , HttpStatus.OK);
    }

    @PostMapping("/members/phoneCheck")
    @ResponseBody
    public ResponseEntity phoneCheck(@RequestParam String smsAuthInput, HttpSession session) {
        final Object obj = session.getAttribute("smsAuth");
        if (obj == null) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.NOT_REQ_SMS)
                    , HttpStatus.OK);
        }

        final String smsAuth = (String) obj;
        log.info("session phoneAuth={}", smsAuth);
        log.info("user.phoneAuth={}", smsAuthInput);

        if (!smsAuth.equals(smsAuthInput)) {
            return new ResponseEntity(new DefaultResponse(StatusCode.UNAUTHORIZED, ResponseMessage.AUTH_FAIL)
                    , HttpStatus.OK);
        }
        session.removeAttribute("smsAuth");
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.AUTH_SUCCESS)
                , HttpStatus.OK);
    }

    @PostMapping("/members/email")
    @ResponseBody
    public ResponseEntity email(@RequestBody Recipient recipient, HttpSession session){
        final String randomNum = createRandomNum();
        final EmailStatus emailStatus = EmailService.sendMail(new Email(recipient), randomNum);
        log.info("recipient = {}", recipient);
        if (emailStatus == EmailStatus.FAIL) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.NOT_REQ_EMAIL)
                    , HttpStatus.OK);
        }
        session.setAttribute("emailAuth", randomNum);
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.REQ_SUCCESS)
                , HttpStatus.OK);
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
        log.info("session.emailAuth={}", emailAuth);
        log.info("user.emailAuth={}", emailAuthInput);

        if (!emailAuth.equals(emailAuthInput)) {
            return new ResponseEntity(new DefaultResponse(StatusCode.BAD_REQUEST, ResponseMessage.AUTH_FAIL)
                    , HttpStatus.OK);
        }
        session.removeAttribute("emailAuth");
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.AUTH_SUCCESS)
                , HttpStatus.OK);
    }

    private static String createRandomNum() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return String.valueOf(generator.nextInt(1000000) % 1000000);
    }
}
