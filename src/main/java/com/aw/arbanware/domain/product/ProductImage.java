package com.aw.arbanware.domain.product;

import com.aw.arbanware.domain.common.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@IdClass(ProductImageKey.class)
@EntityListeners(AuditingEntityListener.class)
public class ProductImage extends BaseTimeEntity implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Id @GeneratedValue
    @Column(name = "SEQUENCE")
    private int sequence;

    private String storedPath;
    private String storedFileName;
    private String originalFileName;
    private Long fileSize;
    @CreatedDate
    private LocalDateTime createdTime;
}
