package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.review.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
class ReviewTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("후기_테스트")
    public void 후기_테스트() throws Exception {
        // given
        new Review();
        // when

        // then
    }
}