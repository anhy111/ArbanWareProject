package com.aw.arbanware.domain.coupon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("DiscountRate")
public class DiscountRate extends Coupon{

    @Column(name = "DISCOUNT_RATE")
    private int DiscountRate; //할인율
}
