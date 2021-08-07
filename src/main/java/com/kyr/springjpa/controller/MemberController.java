package com.kyr.springjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("member")
public class MemberController {

    @GetMapping("signup")
    public String signup(HttpServletResponse res , HttpServletRequest req , Model model) {

        return "member/signup";
    }

    @GetMapping("signin")
    public String signin(HttpServletResponse res , HttpServletRequest req , Model model) {

        return "member/signin";
    }
}
