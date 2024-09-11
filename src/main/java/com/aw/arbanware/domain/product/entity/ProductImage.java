package com.aw.arbanware.domain.product.entity;

import com.aw.arbanware.domain.common.embedded.AttachFileInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class ProductImage implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Embedded
    private AttachFileInfo attachFileInfo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;
}
