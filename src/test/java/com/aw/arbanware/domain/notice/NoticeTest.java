package com.aw.arbanware.domain.notice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
public class NoticeTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("noticeTest")
    public void noticeTest() throws Exception {

        new Notice();
    }

}
