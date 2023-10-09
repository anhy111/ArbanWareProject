package com.aw.arbanware.domain.common.apiobj;

import lombok.Getter;

@Getter
public class DefaultResponse<T> {
    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultResponse(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public DefaultResponse(final int statusCode, final String responseMessage, final T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }
}
