package com.aw.arbanware.domain.common.apiobj;

public class ResponseMessage {
    public static final String REQ_SUCCESS = "인증번호 전송되었습니다";
    public static final String AUTH_SUCCESS = "인증 성공";
    public static final String AUTH_FAIL = "인증번호가 올바르지 않습니다.";
    public static final String NOT_REQ_EMAIL = "인증번호가 전송되지 않은 이메일입니다.";
    public static final String DUPLICATE_LOGIN_ID = "이미 사용중인 아이디입니다.";
    public static final String NOT_DUPLICATE_LOGIN_ID = "사용가능한 아이디입니다.";
    public static final String REQ_SMS_SUCCESS = "SMS 전송 성공";
    public static final String REQ_SMS_FAIL = "SMS 전송 실패";
    public static final String NOT_REQ_SMS = "인증번호가 전송되지 않은 번호입니다.";
}
