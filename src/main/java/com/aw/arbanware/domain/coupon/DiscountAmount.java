package com.aw.arbanware.domain.coupon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("DiscountAmount")
public class DiscountAmount extends Coupon{

    @Column(name = "DISCOUNT_AMOUNT")
    private int DiscountAmount; //할인금액
}
