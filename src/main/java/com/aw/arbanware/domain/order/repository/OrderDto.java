package com.aw.arbanware.domain.order.repository;

import com.aw.arbanware.domain.coupon.MemberCoupon;
import com.aw.arbanware.domain.order.OrderStatus;
import com.aw.arbanware.domain.delivery.entity.Delivery;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {

    private Long id;    // 주문번호
    private Member member;  // 회원번호
    private LocalDateTime orderDate;    // 주문일
    private OrderStatus status;  // 주문상태
    private int shippingAmount; // 배송비
    private int totalPrice;     // 총 주문 금액
    private MemberCoupon memberCoupon;  // 회원 쿠폰 번호

    private int usage_mileage;  // 사용 마일리지
    private int totalPaymentPrice;  // 총 결제 금액

    private Delivery delivery;  // 배송지 번호
    private String orderer; // 주문자
    private String phoneNumber; // 핸드폰
    private String email;       // 이메일

}
