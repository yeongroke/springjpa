package com.kyr.springjpa.controller;

import com.kyr.springjpa.model.bean.MemberDto;
import com.kyr.springjpa.service.MemberService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberController {

    @Setter(onMethod_ = @Autowired)
    private MemberService memberService;

    @GetMapping("contact")
    public String signup(HttpServletResponse res , HttpServletRequest req , Model model) {

        return "member/contact";
    }

    @PostMapping("contact.do")
    public Boolean contactdo(HttpServletResponse res , HttpServletRequest req ,
                               @RequestParam(name = "email" , defaultValue = "", required = false) String email ,
                               @RequestParam(name = "name" , defaultValue = "", required = false) String name ,
                               @RequestParam(name = "conpany" , defaultValue = "", required = false) String conpany){
        log.info("contactdo -> " + email + "==" + name + "==" + conpany);
        Boolean saveFlag = false;

        MemberDto memberDto = new MemberDto();

        memberDto.setEmail(email);
        memberDto.setUsername(name);
        memberDto.setConpany(conpany);

        saveFlag = memberService.memberSignUp(memberDto);

        return saveFlag;
    }

    @GetMapping("signin")
    public String signin(HttpServletResponse res , HttpServletRequest req , Model model) {

        return "member/signin";
    }

    @PostMapping("signin.do")
    public JSONObject signindo(HttpServletResponse res , HttpServletRequest req ,
                               @RequestParam(value = "email" , defaultValue = "", required = false) String email ,
                               @RequestParam(value = "name" , defaultValue = "", required = false) String name){
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }
}
