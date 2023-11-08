package com.aw.arbanware.domain.product.controller;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OrderProductForm {
    private Color color;
    private Size size;
    private int amount = 1;
}
