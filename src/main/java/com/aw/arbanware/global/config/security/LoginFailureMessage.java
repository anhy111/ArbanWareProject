package com.aw.arbanware.global.config.security;

public enum LoginFailureMessage {
    UNKNOWN("알 수 없는 오류로 로그인 요청을 처리할 수 없습니다."),
    USERNAME_NOT_FOUND("존재하지 않는 계정입니다.<br> 회원가입 후 로그인해주세요."),
    SERVICE_EXCEPTION("내부 시스템 문제로 로그인 요청을 처리할 수 없습니다.<br> 관리자에게 문의하세요. "),
    BAD_CREDENTIALS("아이디 또는 비밀번호가 맞지 않습니다.<br> 다시 확인해주세요.");

    private final String message;

    LoginFailureMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
