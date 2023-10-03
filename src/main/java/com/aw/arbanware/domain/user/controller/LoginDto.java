package com.aw.arbanware.domain.user.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    private String loginId;
    private String loginPassword;
}
