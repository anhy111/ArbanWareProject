package com.aw.arbanware.domain.delivery.repository;

import com.aw.arbanware.domain.common.embedded.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDto {

    private Long id;    // 배송지번호

    private String recipient;   // 받는사람

    private Address address;    //주소
    private String phoneNumber;     // 핸드폰
    private String requirements;    // 요청사항
}
