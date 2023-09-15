package com.aw.arbanware.domain.authorization;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.user.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Slf4j
class AuthorizationTest {

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    void authorizationTest() {

        final Member member = new Member();
        member.setName("하이");
        member.setLoginId("dkdlel");
        member.setLoginPassword("qlalfqjsgh");
        member.setAddress(new Address("city","street","03030"));
        member.setEmail("dlapdlf@naver.com");
        member.setBirth("999999");
        member.setMileage(0);
        member.setTelephonedms("041-588-9898");
        member.setDeleteYn(DeleteYn.N);
        final Authorization authorization = new Authorization("회원");
        member.addAuthorization(authorization);

        em.persist(member);
        em.persist(authorization);

        final Member findMember = em.find(Member.class, member.getId());
        log.info("findMember = {}", findMember);
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}