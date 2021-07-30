package com.kyr.springjpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("test")
public class testController {
    public testController(){

    }

    @RequestMapping(value = "req1/{id}", method= RequestMethod.GET)
    public @ResponseBody Map<String, String> req1(@PathVariable String id) {

        Map<String, String> list = new HashMap<>();

        list.put("id", id);
        list.put("pw", "asdfasdf");
        list.put("location", "here");

        return list;
    }
}
