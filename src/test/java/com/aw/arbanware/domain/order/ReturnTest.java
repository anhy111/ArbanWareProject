package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.order.entity.Return;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class ReturnTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("returnTest")
    public void returnTest() throws Exception{
        new Return();
    }
}