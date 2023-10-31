package com.aw.arbanware.domain.user.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("startJPQL")
    public void startJPQL() throws Exception {
        // member1 찾기
        final String qlString = "select m from Member m " +
                "where m.name = :name";

        final Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("name", "테스트멤버")
                .getSingleResult();
        assertThat(findMember.getName()).isEqualTo("테스트멤버");
    }

    @Test
    @DisplayName("startQuerydsl")
    public void startQuerydsl() throws Exception {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        final QMember m = new QMember("m");

        final Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.name.eq("테스트멤버"))
                .fetchOne();

        assertThat(findMember.getName()).isEqualTo("테스트멤버");
    }

}