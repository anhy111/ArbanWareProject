package com.aw.arbanware.domain.inquiry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class InquiryTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("inquiryTest")
    public void inquiryTest() throws Exception{
        new Inquiry();
    }

}