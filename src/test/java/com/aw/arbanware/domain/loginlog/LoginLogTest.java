package com.aw.arbanware.domain.loginlog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class LoginLogTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("로그인_로그_테스트")
    public void 로그인_로그_테스트() throws Exception {
        // given
//        final LoginLog loginLog = new LoginLog();
        // when

        // then
    }

}