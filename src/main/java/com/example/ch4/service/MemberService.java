package com.example.ch4.service;

import com.example.ch4.dto.MemberDto;
import com.example.ch4.entity.Member;
import com.example.ch4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(MemberDto dto) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setAge(dto.getAge());
        member.setMbti(dto.getMbti());
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀원입니다."));
    }
}