package com.kyr.springjpa.service;


import com.kyr.springjpa.model.bean.MemberDTO;

public interface MemberService {
    Long memberSignUp(MemberDTO memberDto);

    MemberDTO findByMemberId(Long id);
}
