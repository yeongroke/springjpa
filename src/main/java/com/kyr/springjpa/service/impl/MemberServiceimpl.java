package com.kyr.springjpa.service.impl;

import com.kyr.springjpa.model.bean.MemberDto;
import com.kyr.springjpa.model.entity.Member;
import com.kyr.springjpa.repository.MemberRepository;
import com.kyr.springjpa.service.MemberService;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
public class MemberServiceimpl implements MemberService {

    @Setter(onMethod_ = @Autowired)
    private MemberRepository memberRepository;

    @Override
    public Long memberSignUp(MemberDto memberDto) {

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .username(memberDto.getUsername())
                .conpany(memberDto.getConpany()).build();

        Long saveMemberId = memberRepository.save(member).getId();

        MemberDto saveMember = findByMemberId(saveMemberId);

        return saveMember.getId();
    }

    @Override
    public MemberDto findByMemberId(Long id) {
        return memberRepository.findByMemberid(id).orElseThrow();
    }
}
