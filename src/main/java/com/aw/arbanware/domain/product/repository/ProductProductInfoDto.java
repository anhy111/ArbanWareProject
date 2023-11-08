package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
public class ProductProductInfoDto {
    private Long productId;
    private String name;
    private Integer price;
    private String thumbnail;
    private LocalDateTime registrationTime;

    @Builder.Default
    private Set<Colors> colors = new LinkedHashSet<>();

    @QueryProjection
    public ProductProductInfoDto(final Long productId, final String name, final Integer price, final String thumbnail, LocalDateTime registrationTime, Set<Colors> colors) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.registrationTime = registrationTime;
        this.colors = colors;
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
