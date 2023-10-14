package com.aw.arbanware.domain.user.service;

import com.aw.arbanware.domain.user.repository.ChangePassword;
import com.aw.arbanware.domain.user.repository.SearchPassword;
import com.aw.arbanware.domain.user.repository.MemberRepository;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member save(Member member) {
        final String encodePw = passwordEncoder.encode(member.getLoginPassword());
        log.info("encodePw = {}", encodePw);
        member.setLoginPassword(encodePw);
        return memberRepository.save(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findSearchPasswordEmailMember(SearchPassword form) {
        return memberRepository.findSearchPasswordEmailMember(form.getLoginId(),form.getName(), form.getEmail());
    }

    public  Optional<Member> findSearchPasswordPhoneMember(SearchPassword form) {
        return memberRepository.findSearchPasswordPhoneMember(form.getLoginId(),form.getName(), form.getPhoneNumber());
    }

    @Transactional
    public void changePassword(ChangePassword form) {
        final Member findMember = memberRepository.findById(form.getId()).get();
        findMember.setLoginPassword(passwordEncoder.encode(form.getLoginPassword()));
    }
}
