package com.aw.arbanware.domain.user.service;

import com.aw.arbanware.domain.user.repository.MemberRepository;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
