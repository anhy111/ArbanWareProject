package com.aw.arbanware.domain.cart.controller;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.common.apiobj.DefaultResponse;
import com.aw.arbanware.domain.common.apiobj.ResponseMessage;
import com.aw.arbanware.domain.common.apiobj.StatusCode;
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

    @PostMapping("/{id}/edit/quantity")
    public ResponseEntity cartQuantityUpdate(@PathVariable("id")Long productInfoId, @AuthenticationPrincipal SecurityUser securityUser, @RequestBody Cart cart) {
        Long memberId = securityUser.getId();
        Cart cartUpdate = cartService.cartQuantityUpdate(memberId, productInfoId, cart);
        return new ResponseEntity(new DefaultResponse(StatusCode.OK, ResponseMessage.NOT_DUPLICATE_LOGIN_ID)
                , HttpStatus.OK);
    }
}