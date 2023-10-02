package com.aw.arbanware.domain.user.repository;

import com.aw.arbanware.domain.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}