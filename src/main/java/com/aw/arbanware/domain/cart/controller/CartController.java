package com.aw.arbanware.domain.cart.controller;

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

import java.util.List;

@Controller
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("")
    public String cartList(Model model, @AuthenticationPrincipal SecurityUser securityUser){
        Long id = securityUser.getId();
        List<Cart> carts = cartService.cartList(id);
        model.addAttribute("cartList", carts);
        return "page/cart/list";
    }
}
