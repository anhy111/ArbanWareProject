package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.Color;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductProductInfoDto {
    private Long productId;
    private String name;
    private Integer price;
    private String thumbnail;
    private LocalDateTime registrationTime;

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final String name, final Integer price, final String thumbnail, LocalDateTime registrationTime) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.registrationTime = registrationTime;
    }
}
