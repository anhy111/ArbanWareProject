package com.aw.arbanware.domain.notification;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class NotificationTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("notificationTest")
    public void notificationTest() throws Exception{
//        new Notification();
    }
}