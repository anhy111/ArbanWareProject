package com.aw.arbanware.domain.payment.service;

import com.aw.arbanware.domain.payment.PaymentMethod;
import com.aw.arbanware.domain.payment.PaymentStatus;
import com.aw.arbanware.domain.payment.entity.Payment;
import com.aw.arbanware.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

import static java.util.Base64.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public JSONObject callApiAuth(String paymentKey, String orderId, Long amount) {
        String secretKey = readKey();
        Encoder encoder = getEncoder();
        byte[] encodedBytes = new byte[0];
        try {
            encodedBytes = encoder.encode(secretKey.getBytes("UTF-8"));
            String authorizations = "Basic "+ new String(encodedBytes, 0, encodedBytes.length);

            paymentKey = URLEncoder.encode(paymentKey, StandardCharsets.UTF_8);
            URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", authorizations);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            JSONObject obj = new JSONObject();
            obj.put("paymentKey", paymentKey);
            obj.put("orderId", orderId);
            obj.put("amount", amount);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(obj.toString().getBytes("UTF-8"));

            int code = connection.getResponseCode();
            boolean isSuccess = code == 200 ? true : false;
            InputStream responseStream = isSuccess? connection.getInputStream(): connection.getErrorStream();

            Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            responseStream.close();

            if (isSuccess){
                log.info("승인요청응답 OK");
            } else {
                log.info("승인요청응답 예외발생 {}", code);
                log.info("message={}, code={}", jsonObject.get("message"), jsonObject.get("code"));
            }
            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String  readKey() {
        final Properties properties = new Properties();
        String path = "./toss.properties";

        try(FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.warn("Failed to load file from the {} file", path);
        }

        return properties.getProperty("secretKey");
    }

    public Payment paymentSave(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment paymentSuccess(JSONObject paymentJson, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("해당 결제가 없습니다."));
        payment.setPaymentKey((String)paymentJson.get("paymentKey"));
        payment.setMethod(PaymentMethod.findStatus((String)paymentJson.get("method")));
        payment.setStatus(PaymentStatus.COMPLETE_PAYMENT);
        return payment;
    }

    public Payment paymentCancel(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("해당 결제가 없습니다."));
        payment.setStatus(PaymentStatus.EXPIRES_PAYMENT);
        return payment;
    }
}
