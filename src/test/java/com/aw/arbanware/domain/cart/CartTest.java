package com.aw.arbanware.domain.cart;

import com.aw.arbanware.domain.cart.entity.Cart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class CartTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("장바구니_테스트")
    public void 장바구니_테스트() throws Exception {
        // given
        new Cart();
        // when

        // then
    }
}