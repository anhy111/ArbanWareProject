package com.aw.arbanware.domain.user.entity;

import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.delivery.entity.Delivery;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@DiscriminatorValue("MEMBER")
@PrimaryKeyJoinColumn(name = "MEMBER_ID")
public class Member extends User {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;  //배송지

    @Embedded
    private Address address;    // 주소

    private String name;    // 성명
    private String telephonedms;// 유선전화
    private String phoneNumber; //핸드폰
    private String email;   //이메일
    private String birth;   //생년월일
    private int mileage;    //마일리지

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", telephonedms='" + telephonedms + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", mileage=" + mileage +
                "} " + super.toString();
    }
}
