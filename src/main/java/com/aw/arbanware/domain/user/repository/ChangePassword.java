package com.aw.arbanware.domain.user.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ChangePassword {
    private Long id;
    private String loginPassword;
}
