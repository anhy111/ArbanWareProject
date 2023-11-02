package com.aw.arbanware.domain.product.repository;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ProductProductInfoDto {
    private Long productId;
    private String name;
    private Integer price;
    private String thumbnail;

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final String name, final Integer price, final String thumbnail) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }
}
