package com.aw.arbanware.domain.exchange;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import com.aw.arbanware.domain.orderproduct.OrderProduct;
import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Exchange {
    @Id @GeneratedValue
    @Column(name = "EXCHANGE_ID")
    private Long id;    // 교환번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct; //주문상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;    //상품정보번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin;        // 담당자번호

    @Enumerated(EnumType.STRING)
    private ExchangeStatus status;  //상태

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime applicationDate;  //신청일

    private LocalDateTime acceptanceDate;   //접수일

}
