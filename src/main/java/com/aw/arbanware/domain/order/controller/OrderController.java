package com.aw.arbanware.domain.order.controller;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.order.service.OrderService;
import com.aw.arbanware.domain.product.controller.OrderProductForm;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.product.service.ProductInfoService;
import com.aw.arbanware.domain.product.service.ProductService;
import com.aw.arbanware.domain.user.entity.Member;
import com.aw.arbanware.domain.user.service.MemberService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final ProductInfoService productInfoService;
    private final ProductService productService;

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String orderWrite(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        Long id = securityUser.getId();
        log.info("id={}", id);
        Optional<Member> member = memberService.findById(id);
        List<Cart> carts = cartService.cartList(id);
        model.addAttribute("cartList", carts);
        model.addAttribute("user", member);
        return "page/order/register";
    }

    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String orderNew(Model model, @AuthenticationPrincipal SecurityUser securityUser, @ModelAttribute OrderProductForm orderProductForm) {
        Long memberId = securityUser.getId();
        Optional<Member> member = memberService.findById(memberId);
        ProductInfo productInfo = productInfoService.findByProductAndColorAndSize(orderProductForm);
        Cart cart = cartService.cartOrder(memberId, productInfo.getId());
        List<Cart> carts = new ArrayList<Cart>();
        carts.add(cart);

        model.addAttribute("user", member);
        model.addAttribute("cartList", carts);
        return "page/order/register";
    }

    @GetMapping("/check/new")
    @PreAuthorize("isAuthenticated()")
    public String checkOrderWrite(Model model, @AuthenticationPrincipal SecurityUser securityUser, @RequestParam List<Long> checkedValue) {
        Long id = securityUser.getId();
        List<Cart> carts = new ArrayList<Cart>();
        Optional<Member> member = memberService.findById(id);

        for (int i = 0; i < checkedValue.size(); i++) {
            Cart cart = cartService.cartOrder(id, checkedValue.get(i));
            carts.add(cart);
        }

        model.addAttribute("cartList", carts);
        model.addAttribute("user", member);

        log.info(" productInfo={}", checkedValue);

        return "page/order/register";
    }

    @PostMapping("/{id}/new")
    @ResponseBody
    public Order deliverySave(@PathVariable("id")Long memberId, @RequestBody Order order) {
        Order orderRegister = orderService.orderRegister(order);
        orderRegister.getId();
        return orderRegister;
    }

}
