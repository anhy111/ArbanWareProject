package com.aw.arbanware.domain.order.controller;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
import com.aw.arbanware.domain.delivery.entity.Delivery;
import com.aw.arbanware.domain.delivery.service.DeliveryService;
import com.aw.arbanware.domain.order.service.OrderService;
import com.aw.arbanware.domain.payment.service.PaymentService;
import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final MemberService memberService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping("/new")
    public String orderWrite(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        Long id = securityUser.getId();
        log.info("id={}", id);
        if (id != null) {
            Optional<Member> member = memberService.findById(id);
            if (member.get().getDelivery() != null) {
                log.info(" delivery 으ㅏ아아아아아앙아아아아아아아아아아아아 " + member.get().getDelivery());
            }
            List<Cart> carts = cartService.cartList(id);
            model.addAttribute("cartList", carts);
            model.addAttribute("user", member);
        }
        return "page/order/register";
    }

    @GetMapping("/success")
    public String orderSuccess(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount) {

        if (paymentService.callApiAuth(paymentKey, orderId, amount)) {
            log.info("결제 성고옹");
        } else {
            log.info("결제 실패 ㅠㅠㅠ");
        }
        return "page/order/register_success";
    }

    @GetMapping("/fail")
    public String orderFail() {

        return "page/order/register_fail";
    }

    @PostMapping("/{id}/new")
    public ResponseEntity deliverySave(@PathVariable("id") Long memberId, @RequestBody Delivery delivery) {
        memberService.deliverySave(memberId, delivery);

        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }

}
