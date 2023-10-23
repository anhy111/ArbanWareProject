package com.aw.arbanware.domain.order.entity;

import com.aw.arbanware.domain.order.OrderCancelProgress;
import com.aw.arbanware.domain.order.OrderCancelStatus;
import com.aw.arbanware.domain.orderproduct.OrderProduct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderCancel {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "order_cancel_seq")
    @SequenceGenerator(name = "order_cancel_seq",sequenceName = "ORDER_CANCEL_SEQUENCE",allocationSize = 1)
    @Column(name = "ORDER_CANCEL_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REFUND_ID")
    private Refund refund; //환불 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct; //주문 상품 번호

    @Enumerated(EnumType.STRING)
    private OrderCancelStatus status; //입금 상태

    @Enumerated(EnumType.STRING)
    private OrderCancelProgress progress; //진행 상태

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime applyDate; //신청일

    private int amount; //취소금액

}
