package com.aw.arbanware.domain.category;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
@Slf4j
class CategoryTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("카테고리_테스트")
    @Rollback(false)
    public void categoryTest() throws Exception {
        // given
        final Category top = new Category("상의");
        em.persist(top);

        final Category tShirt = new Category("반팔");
        tShirt.setParent(top);
        em.persist(tShirt);

        // when
        final Category findCategory = em.find(Category.class, tShirt.getId());
        log.info("findCategory = {}", findCategory);

        // then
        Assertions.assertThat(findCategory.getParent()).isEqualTo(top);
    }

}