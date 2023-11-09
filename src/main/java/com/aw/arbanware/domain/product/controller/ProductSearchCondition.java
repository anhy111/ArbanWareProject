package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.List;


@Getter @Setter
@ToString
public class ProductSearchCondition {
    private List<Color> colors;
    private List<Size> sizes;

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    private String minPrice;

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    private String maxPrice;

    private String name;
    private String category;
    private int page;
    private int pageSize=20;
    private String sortProperty = "registrationTime";
}
