package com.aw.arbanware.infra.email;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Email {
    private String senderAddress = "arbanAdmin@arbanware.com";
    private String senderName = "Arban Ware";
    private String title = "[Arban Ware] 이메일 인증번호 안내";
    private String body = new StringBuilder()
            .append("안녕하세요, 고객님<br>")
            .append("요청하신 Arban Ware 이메일 인증번호를 안내 드립니다.<br>")
            .append("아래 번호를 입력하여 Arban Ware 인증 절차를 완료해 주세요.<br>")
            .append("인증번호 : <b style='font-size: 16px'>${authNum}</b><br><br>")
            .append("본 인증번호는 30분 후에 만료됩니다.<br><br>")
            .append("Arban Ware 드림").toString();
    private Map<String, String> parameters = new HashMap<>();
    private Recipient recipient;
    private boolean individual = true;

    public Email(final Recipient recipient) {
        this.recipient = recipient;
    }
}
