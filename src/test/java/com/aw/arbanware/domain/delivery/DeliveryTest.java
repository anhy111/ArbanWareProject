package com.aw.arbanware.domain.delivery;

import com.aw.arbanware.domain.authorization.Authorization;
import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.common.embedded.Address;
import com.aw.arbanware.domain.delivery.entity.Delivery;
import com.aw.arbanware.domain.user.entity.Member;
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
@Slf4j
@Transactional
class DeliveryTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("배송_테스트")
    @Rollback(false)
    public void 배송_테스트() throws Exception {
        // given
        final Long memberId = createMember();
        final Delivery delivery = new Delivery(new Address("city", "street", "30000"));
        delivery.setRecipient("받는사람");
        delivery.setPhoneNumber("01011115555");
        delivery.setRequirements("요청사항");

        em.persist(delivery);

        Member findMember = em.find(Member.class, memberId);
        findMember.setDelivery(delivery);

        em.flush();
        em.clear();
        // when
        findMember = em.find(Member.class, memberId);

        log.info("findMember = {}", findMember);

        // then
        Assertions.assertThat(findMember.getDelivery()).isEqualTo(delivery);
    }

    private Long createMember() {
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
        return member.getId();
    }
}