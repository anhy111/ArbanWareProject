package com.aw.arbanware.domain.payment;

public enum PaymentMethod {

    CARD("카드"),
    EASY_PAY("간편결제"),
    VIRTUAL_ACCOUNT("가상계좌"),
    MOBILE_PHONE("휴대폰"),
    TRANSFER("계좌이체"),
    CULTURE_GIFT_CERTIFICATE("문화상품권"),
    BOOK_GIFT_CERTIFICATE("도서문화상품권"),
    GAME_GIFT_CERTIFICATE("게임문화상품권");

    private String krName;

    PaymentMethod(String krName) {
        this.krName = krName;
    }

    public String getKrName() {
        return krName;
    }

    public static PaymentMethod findStatus(String krName) {
        for (PaymentMethod status : PaymentMethod.values()) {
            if (status.getKrName().equals(krName)) {
                return status;
            }
        }
        return null;
    }
}
