package com.aw.arbanware.domain.orderproduct.entity;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.order.entity.Order;
import com.aw.arbanware.domain.orderproduct.OrderProductStatus;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class OrderProduct extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "order_product_seq")
    @SequenceGenerator(name = "order_product_seq",sequenceName = "ORDER_PRODUCT_SEQUENCE",allocationSize = 1)
    @Column(name = "ORDER_PRODUCT_ID")
    private Long id;    //주문상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;    //주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;    // 상품정보번호

    @Enumerated(EnumType.STRING)
    private OrderProductStatus status = OrderProductStatus.ORDER;  // 상태

    private int price;      //판매가
    private int discountPrice;//할인금액
    private int amount;     //수량
}
