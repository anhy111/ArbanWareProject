package com.aw.arbanware.infra.sms;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class SmsService {

    private static final DefaultMessageService messageService;
    private static final String sendPhoneNumber;

    static {
        final Properties properties = new Properties();
        String path = "./smsApi.properties";

        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.warn("Failed to load file from the {} file", path);
        }

        final String apiKey = properties.getProperty("apiKey");
        final String apiSecretKey = properties.getProperty("apiSecretKey");
        sendPhoneNumber = properties.getProperty("phoneNumber");
        if (apiKey == null || apiSecretKey == null) {
            log.warn("wrong properties file not exist 'apiKey' or 'apiSecretKey'");
        }

        log.info("apiKey = {}", apiKey);
        log.info("apiSecretKey = {}", apiSecretKey);
        messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    public static SingleMessageSentResponse sendSms(String phoneNumber,String randomNum) {
        log.info("phoneNumber = {}", phoneNumber);
        log.info("randomNum = {}", randomNum);

        Message message = new Message();
        message.setFrom(sendPhoneNumber);
        message.setTo(phoneNumber);
        final String formatStr = String.format("Arban Ware 인증번호 [%s]를 \n입력해주세요.", randomNum);
        message.setText(formatStr);
        return messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
