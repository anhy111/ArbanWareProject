package com.aw.arbanware.domain.payment.controller;

import com.aw.arbanware.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/success")
    public String orderSuccess(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount) {

//        if (paymentService.callApiAuth(paymentKey, orderId, amount)) {
//            log.info("결제 성고옹");
//        } else {
//            log.info("결제 실패 ㅠㅠㅠ");
//        }

        JSONObject jsonObject = paymentService.callApiAuth(paymentKey, orderId, amount);
        log.info(">>>>>>>에?" + jsonObject);

        return "page/order/register_success";
    }

    @GetMapping("/fail")
    public String orderFail() {

        return "page/order/register_fail";
    }

}
