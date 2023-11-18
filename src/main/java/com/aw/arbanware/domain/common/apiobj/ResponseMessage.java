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
    public static final String ALREADY_REVIEW = "이미 작성된 후기가 있습니다.";
    public static final String NOT_ORDER = "구매하지 않은 상품에는 후기를 작성할 수 없습니다. 주문한 상품이라면 수령한 후에 작성해주세요.";
    public static final String NOT_FOUND_REVIEW = "등록되지 않은 후기입니다.";
    public static final String FORBIDDEN_REVIEW_DELETE = "후기를 등록한 사용자가 아닙니다.";
    public static final String CAN_WRITE_REVIEW = "후기를 작성할 수 있는 사용자입니다.";
    public static final String REG_REVIEW_SUCCESS = "후기가 등록되었습니다.";
    public static final String DELETE_REVIEW_SUCCESS = "후기가 삭제되었습니다.";
    public static final String IS_ANONYMOUS = "로그인되지 않았습니다.";
}
