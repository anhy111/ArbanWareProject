package com.aw.arbanware.domain.order.entity;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.order.OrderStatus;
import com.aw.arbanware.domain.orderproduct.entity.OrderProduct;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "ORDERS")
public class Order extends BaseTimeEntity implements Persistable<String> {
    @Id
    @Column(name = "ORDER_ID")
    private String id;    // 주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;  // 회원번호

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProduct; //주문상품정보

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime orderDate;    // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문상태
    private int shippingAmount; // 배송비
    private int totalPrice;     // 총 주문 금액

    private int usage_mileage;  // 사용 마일리지
    private int totalPaymentPrice;  // 총 결제 금액

    private String orderer; // 주문자
    private String ordererPhoneNumber; // 핸드폰
    private String email;       // 이메일

    private String recipient;   // 받는사람
    private Address address;    //주소
    private String recipientPhoneNumber;     // 핸드폰
    private String requirements;    // 요청사항

    @Override
    public boolean isNew() {
        return  super.getRegistrationTime() == null;
    }

}
