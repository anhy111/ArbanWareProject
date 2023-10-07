package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.common.embedded.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RegisterMemberForm {


    private String name;    // 성명
    private String loginId; // 로그인 아이디
    private String loginPassword; // 로그인 비밀번호
    private String telephonedms;// 유선전화
    private String phoneNumber; //핸드폰
    private String email;   //이메일
    private String birth;   //생년월일
    private Address address;    // 주소
}
