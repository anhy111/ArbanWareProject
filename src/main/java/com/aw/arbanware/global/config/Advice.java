package com.aw.arbanware.global.config;

import com.aw.arbanware.domain.cart.service.CartService;
import com.aw.arbanware.domain.category.entity.Category;
import com.aw.arbanware.domain.category.service.CategoryService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class Advice {

    private final CategoryService categoryService;
    private final CartService cartService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAllCategories();
    }

    @ModelAttribute("cartCount")
    public int cartCount(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return 0;
        }
        final SecurityUser user = (SecurityUser) authentication.getPrincipal();
        return cartService.memberCartCount(user.getId());
    }
}
