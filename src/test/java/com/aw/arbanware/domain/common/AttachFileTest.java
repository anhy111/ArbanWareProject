package com.aw.arbanware.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AttachFileTest {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("첨부파일_테스트")
    public void 첨부파일_테스트() throws Exception {
        // given

        // when

        // then
    }

}