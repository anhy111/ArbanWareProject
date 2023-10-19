package com.aw.arbanware.domain.order.controller;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/new")
    public String orderWrite(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        Long id = securityUser.getId();
        log.info("id={}" + id);
        if (id != null) {
            Optional<Member> member = memberService.findById(id);
            if (member.get().getDelivery() != null) {
                log.info(" delivery 으ㅏ아아아아아앙아아아아아아아아아아아아 ", member.get().getDelivery());
            }
            List<Cart> carts = cartService.cartList(id);
            model.addAttribute("cartList", carts);
            model.addAttribute("user", member);
        }
        return "page/order/register";
    }

    @GetMapping("/success")
    public String orderSuccess(@RequestParam String paymentKey, @RequestParam String orderId, @RequestParam Long amount) {

        return "page/order/register_success";
    }

    @GetMapping("/fail")
    public String orderFail() {

        return "page/order/register_fail";
    }
}
