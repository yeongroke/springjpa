package com.kyr.springjpa.service.impl;

import com.kyr.springjpa.model.bean.MemberDTO;
import com.kyr.springjpa.model.entity.Member;
import com.kyr.springjpa.repository.MemberRepository;
import com.kyr.springjpa.service.MemberService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceimpl implements MemberService {

    @Setter(onMethod_ = @Autowired)
    private MemberRepository memberRepository;

    @Override
    public Long memberSignUp(MemberDTO memberDto) {

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .conpany(memberDto.getConpany())
                .password(memberDto.getPassword()).build();

        Long saveMemberId = memberRepository.save(member).getId();

        MemberDTO saveMember = findByMemberId(saveMemberId);

        return saveMember.getId();
    }

    @Override
    public MemberDTO findByMemberId(Long id) {
        return memberRepository.findByMemberid(id).orElseThrow();
    }
}
