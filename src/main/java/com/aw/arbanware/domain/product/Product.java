package com.aw.arbanware.domain.product;

import com.aw.arbanware.domain.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue
    private Long productId;

    private String productName;

}
