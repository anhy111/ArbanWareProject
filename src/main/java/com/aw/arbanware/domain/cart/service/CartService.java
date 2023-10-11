package com.aw.arbanware.domain.cart.service;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.entity.CartKey;
import com.aw.arbanware.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> cartList(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

}
