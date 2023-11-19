package com.aw.arbanware.domain.order;

public enum OrderStatus {
    CANCEL_PAYMENT("결제취소"),
    AWAITING_PAYMENT("결제중"),
    EXPIRES_PAYMENT("결제만료"),
    COMPLETE_PAYMENT("결제 완료"),
    PROCESSING("배송준비"),
    IN_DELIVERY("배송중"),
    DELIVERED("배송완료");

    private String krName;

    OrderStatus(String krName) {
        this.krName = krName;
    }

    public String getKrName(){
        return krName;
    }

    public static OrderStatus findStatus(String krName) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getKrName().equals(krName)) {
                return status;
            }
        }
        return null;
    }

}
