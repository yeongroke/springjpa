package com.kyr.springjpa.service;


import com.kyr.springjpa.model.bean.MemberDto;
import com.kyr.springjpa.model.entity.Member;

public interface MemberService {
    Boolean memberSignUp(MemberDto memberDto);

    MemberDto findByMemberId(Long id);
}
