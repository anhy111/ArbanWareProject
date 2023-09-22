package com.aw.arbanware.domain.orderproduct;

import com.aw.arbanware.domain.common.BaseTimeEntity;
import com.aw.arbanware.domain.order.Order;
import com.aw.arbanware.domain.product.ProductInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderProduct extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "ORDER_PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;

    @Enumerated(EnumType.STRING)
    private OrderProductStatus status;

    private int price;
    private int discountPrice;
    private int amount;
}
