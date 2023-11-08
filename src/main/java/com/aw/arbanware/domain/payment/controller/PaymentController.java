package com.aw.arbanware.domain.payment.controller;

import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.order.service.OrderService;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.orderproduct.service.OrderProductService;
import com.aw.arbanware.domain.payment.entity.Payment;
import com.aw.arbanware.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;

    @GetMapping("/success")
    public String orderSuccess(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount, @RequestParam Long paymentId, Model model) {

        JSONObject jsonObject = paymentService.callApiAuth(paymentKey, orderId, amount);
        orderService.orderSuccess(orderId);
        paymentService.paymentSuccess(jsonObject, paymentId);
        OrderProduct orderProduct = orderProductService.orderProductFind(orderId);
        Order order = orderService.orderFind(orderId);
        model.addAttribute("orderProduct", orderProduct);
        model.addAttribute("order", order);

        return "page/order/register_success";
    }

    @GetMapping("/fail")
    public String orderFail() {

        return "page/order/register_fail";
    }

    @PostMapping("/new")
    @ResponseBody
    public Payment paymentSave(@RequestBody Payment payment) {
        return paymentService.paymentSave(payment);
    }

    @PostMapping("/cancel/{paymentId}/{orderId}")
    @ResponseBody
    public ResponseEntity paymentCancel(@PathVariable("paymentId")Long paymentId, @PathVariable("orderId")String orderId) {
        paymentService.paymentCancel(paymentId);
        orderService.orderCancel(orderId);
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }
}
