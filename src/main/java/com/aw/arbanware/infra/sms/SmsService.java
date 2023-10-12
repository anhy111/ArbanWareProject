package com.aw.arbanware.infra.sms;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Slf4j
public class SmsService {

    private static final DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(
            "NCSZB6C5M2PZ5XTN", "ZNBCI4SKYJGKCQ5MIHZTW1I98WTYDRQY", "https://api.coolsms.co.kr");

    public static SingleMessageSentResponse sendSms(String phoneNumber,String randomNum) {
        log.info("phoneNumber = {}", phoneNumber);
        log.info("randomNum = {}", randomNum);

        Message message = new Message();
        message.setFrom("01094366849");
        message.setTo(phoneNumber);
        final String formatStr = String.format("Arban Ware 인증번호 [%s]를 \n입력해주세요.", randomNum);
        message.setText(formatStr);
        return messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
