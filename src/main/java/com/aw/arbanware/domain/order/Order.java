package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.coupon.MemberCoupon;
import com.aw.arbanware.domain.delivery.Delivery;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "ORDERS")
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq",sequenceName = "ORDERS_SEQUENCE",allocationSize = 1)
    @Column(name = "ORDER_ID")
    private Long id;    // 주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  // 회원번호

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문상태
    private int shippingAmount; // 배송비
    private int totalPrice;     // 총 주문 금액

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_COUPON_ID")
    private MemberCoupon memberCoupon;  // 회원 쿠폰 번호

    private int usage_mileage;  // 사용 마일리지
    private int totalPaymentPrice;  // 총 결제 금액

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;  // 배송지 번호
    private String orderer; // 주문자
    private String phoneNumber; // 핸드폰
    private String email;       // 이메일
}
