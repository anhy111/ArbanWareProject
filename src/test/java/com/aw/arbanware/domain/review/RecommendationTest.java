package com.aw.arbanware.domain.review;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RecommendationTest {
    
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("후기_추천_테스트")
    public void 후기_추천_테스트() throws Exception {
        // given
        
        // when
        
        // then
    }

}