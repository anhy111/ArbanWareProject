package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.order.entity.Refund;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class RefundTest {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("refundTest")
    public void refundTest() throws Exception{
        new Refund();
    }
}