package com.aw.arbanware.domain.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductInfoTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상품_정보_테스트")
    public void 상품_정보_테스트() throws Exception {
        // given
        new ProductInfo();
        // when

        // then
    }
}