package com.aw.arbanware.domain.coupon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class CouponTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("couponTest")
    public void couponTest() throws Exception{
//        new Coupon();
    }

    @Test
    @DisplayName("memberCouponTest")
    public void memberCouponTest() throws Exception{
//        new MemberCoupon();
    }
}