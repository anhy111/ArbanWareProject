package com.aw.arbanware.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;


@SpringBootTest
@Slf4j
class OrderTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("주문_테스트")
    public void 주문_테스트() throws Exception {
        // given

        // when

        // then
    }

}