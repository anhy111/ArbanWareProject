package com.aw.arbanware.infra.email;

import com.nbp.ncp.nes.ApiClient;
import com.nbp.ncp.nes.ApiResponse;
import com.nbp.ncp.nes.api.V1Api;
import com.nbp.ncp.nes.auth.PropertiesFileCredentialsProvider;
import com.nbp.ncp.nes.exception.ApiException;
import com.nbp.ncp.nes.exception.SdkException;
import com.nbp.ncp.nes.marshaller.FormMarshaller;
import com.nbp.ncp.nes.marshaller.JsonMarshaller;
import com.nbp.ncp.nes.marshaller.XmlMarshaller;
import com.nbp.ncp.nes.model.EmailSendRequest;
import com.nbp.ncp.nes.model.EmailSendRequestRecipients;
import com.nbp.ncp.nes.model.EmailSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    public static String sendMail(final Email email) {
        email.getParameters().put("authNum", createRandomNum());
        get(email);
        return null;
    }

    private static void get(final Email email) {
        ApiClient apiClient = new ApiClient.ApiClientBuilder()
                .addMarshaller(JsonMarshaller.getInstance())
                .addMarshaller(XmlMarshaller.getInstance())
                .addMarshaller(FormMarshaller.getInstance())
                .setCredentials(new PropertiesFileCredentialsProvider("lib/credentials.properties").getCredentials())
                .setLogging(true)
                .build();

        V1Api apiInstance = new V1Api(apiClient);
        List<EmailSendRequestRecipients> esrrList = new ArrayList<>();
        EmailSendRequestRecipients esrr = new EmailSendRequestRecipients();
        esrr.setAddress(email.getRecipient().getAddress());
        esrr.setName(email.getRecipient().getName());
        esrr.setType("R");
        esrrList.add(esrr);

        EmailSendRequest requestBody = new EmailSendRequest();
        requestBody.setBody(email.getBody());
        requestBody.setRecipients(esrrList);
        requestBody.setSenderAddress(email.getSenderAddress());
        requestBody.setSenderName(email.getSenderName());
        requestBody.setTitle(email.getTitle());
        requestBody.setConfirmAndSend(false);
        requestBody.setParameters(email.getParameters());

        String X_NCP_LANG = "ko-KR"; // String | 언어 (ko-KR, en-US, zh-CN), default:en-US
        log.info("requestBody = {}", requestBody);

        try {
            // Handler Successful response
            ApiResponse<EmailSendResponse> result = apiInstance.mailsPost(requestBody, X_NCP_LANG);
        } catch (ApiException e) {
            // Handler Failed response
            int statusCode = e.getHttpStatusCode();
            Map<String, List<String>> responseHeaders = e.getHttpHeaders();
            InputStream byteStream = e.getByteStream();
            e.printStackTrace();
        } catch (SdkException e) {
            // Handle exceptions that occurred before communication with the server
            e.printStackTrace();
        }
    }

    private static String createRandomNum() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return String.valueOf(generator.nextInt(1000000) % 1000000);
    }

}
