package com.aw.arbanware.domain.user.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@ToString
public class SearchPassword {
    @NotNull @NotBlank
    private String loginId;

    @NotNull @NotBlank
    private String name;

    private SearchPasswordType searchPasswordType = SearchPasswordType.EMAIL;

    private String email;

    private String phoneNumber;

}
