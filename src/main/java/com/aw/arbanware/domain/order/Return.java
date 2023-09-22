package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.orderproduct.OrderProduct;
import com.aw.arbanware.domain.user.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Return {

    @Id
    @GeneratedValue
    @Column(name = "RETURN_ID")
    private Long id; //반품 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct; //주문 상품 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin; //담당자 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REFUND_ID")
    private Refund refund; //환불 번호

    @Enumerated(EnumType.STRING)
    private ReturnStatus status; //상태

    private String reason; //반품사유

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime applyDate; //신청일
    private LocalDateTime RegistrationDate; //접수일
    private Long attachFileId; //첨부파일번호
}
