package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.data.domain.Sort.*;

@Getter @Setter
@ToString
public class ProductSearchCondition {
    List<Color> colors;
    List<Size> sizes;

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    String minPrice;

    @Pattern(regexp = "^[\\d]*$", message = "숫자만 입력해주세요")
    String maxPrice;

    String name;

    String sort;
}
