package com.aw.arbanware.domain.cart.service;

import com.aw.arbanware.domain.cart.entity.Cart;
import com.aw.arbanware.domain.cart.entity.CartKey;
import com.aw.arbanware.domain.cart.repository.CartRepository;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<Cart> cartList(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    @Transactional
    public Cart cartQuantityUpdate(Long memberId, Long productInfoId, Cart cart) {
        Cart quantityUp = cartRepository.findByMemberIdAndProductInfoId(memberId, productInfoId);
        quantityUp.setQuantity(cart.getQuantity());
        return quantityUp;
    }

    public Cart cartSave(ProductInfo productInfo, Long memberId, int amount) {
        Cart cart = new Cart();
        cart.setProductInfoId(productInfo.getId());
        cart.setMemberId(memberId);
        cart.setQuantity(amount);
        cartRepository.save(cart);
        return cart;
    }

    public Cart cartOrder(Long memberId, Long productInfoId) {
        Cart cartOrder = cartRepository.findByMemberIdAndProductInfoId(memberId, productInfoId);
        return cartOrder;
    }

    public void cartOneDelete(Long memberId, Long productInfoId) {
        CartKey cartKey = new CartKey();

        cartKey.setMemberId(memberId);
        cartKey.setProductInfoId(productInfoId);

        cartRepository.deleteById(cartKey);
    }

    public void cartDelete(SecurityUser user, List<OrderProduct> orderProduct){
        if (orderProduct == null) {
            return;
        }

        List<Cart> carts = new ArrayList<>();
        for (OrderProduct orderProducts : orderProduct) {
            final Cart cart = new Cart();
            cart.setMemberId(user.getId());
            cart.setProductInfoId(orderProducts.getProductInfo().getId());
            carts.add(cart);
        }
        cartRepository.deleteAll(carts);
    }

    public int memberCartCount(Long memberId) {
        return cartRepository.findCountByMember(memberId);
    }

}
