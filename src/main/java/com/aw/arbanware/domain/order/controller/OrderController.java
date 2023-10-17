package com.aw.arbanware.domain.order.controller;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.service.CartService;
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

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;

    @GetMapping("/new")
    public String orderWrite(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        Long id = securityUser.getId();
        log.info("id={}" + id);
        if (id != null) {
            List<Cart> carts = cartService.cartList(id);
            model.addAttribute("cartList", carts);
            model.addAttribute("user", securityUser);
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
