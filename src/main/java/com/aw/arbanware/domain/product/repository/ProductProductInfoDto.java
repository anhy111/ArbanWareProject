package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
public class ProductProductInfoDto {
    private Long productId;
    private String name;
    private Integer price;
    private String thumbnail;
    private LocalDateTime registrationTime;
    private Long buyCount;

    @Builder.Default
    private Set<Colors> colors = new LinkedHashSet<>();

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final String name, final Integer price, final String thumbnail,
                                 LocalDateTime registrationTime, Set<Colors> colors, Long buyCount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.registrationTime = registrationTime;
        this.colors = colors;
        this.buyCount = buyCount;
    }

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final LocalDateTime registrationTime, Long buyCount) {
        this.productId = productId;
        this.registrationTime = registrationTime;
        this.buyCount = buyCount;
    }

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final LocalDateTime registrationTime) {
        this.productId = productId;
        this.registrationTime = registrationTime;
    }

    @Getter @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @EqualsAndHashCode(of = "color")
    public static class Colors{
        private Color color;

        @QueryProjection
        public Colors(final Color color) {
            this.color = color;
        }
    }

}
