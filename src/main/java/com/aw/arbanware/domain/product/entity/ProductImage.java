package com.aw.arbanware.domain.product.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class ProductImage implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "PRODUCT_IMAGE_SEQUENCE")
    @Column(name = "PRODUCT_IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private String storedPath;
    private String storedFileName;
    private String originalFileName;
    private Long fileSize;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;
}
