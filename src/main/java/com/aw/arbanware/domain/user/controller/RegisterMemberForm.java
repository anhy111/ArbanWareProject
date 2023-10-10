package com.aw.arbanware.domain.user.controller;

import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@ToString
public class RegisterMemberForm {

    @NotBlank @NotNull
    private String name;    // 성명

    @NotBlank @NotNull
    @Range(min = 4, max = 16)
    private String loginId; // 로그인 아이디

    @NotBlank @NotNull
    private String loginPassword; // 로그인 비밀번호

    @NotNull
    private String telephonedms;// 유선전화

    @NotBlank @NotNull
    private String phoneNumber; //핸드폰

    @NotBlank @NotNull
    @Email
    private String email;   //이메일

    @NotNull
    private String birth;   //생년월일

    @NotBlank @NotNull
    private String zipcode;
    private String city;
    private String street;

    public static Member createMember(RegisterMemberForm form) {
        final Member member = new Member();
        member.setName(form.getName());
        member.setAddress(new Address(form.getCity(), form.getStreet(), form.getZipcode()));
        member.setBirth(form.getBirth());
        member.setEmail(form.getEmail());
        member.setTelephonedms(form.getTelephonedms());
        member.setPhoneNumber(form.getPhoneNumber());
        member.setLoginId(form.getLoginId());
        member.setLoginPassword(form.getLoginPassword());
        return member;
    }
}
