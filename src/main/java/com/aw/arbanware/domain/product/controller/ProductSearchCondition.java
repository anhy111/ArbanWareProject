package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.springframework.data.domain.Sort.*;

@Getter @Setter
@ToString
public class ProductSearchCondition {
    List<Color> colors;
    List<Size> sizes;
    String minPrice;
    String maxPrice;
    String name;
    String sort;
}
