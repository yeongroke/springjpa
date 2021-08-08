package com.kyr.springjpa.controller;

import com.kyr.springjpa.model.bean.GuestDto;
import com.kyr.springjpa.model.entity.Guest;
import com.kyr.springjpa.service.GuestService;
import com.kyr.springjpa.service.OpenLayerService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Setter(onMethod_ = @Autowired)
    private GuestService guestService;

    @Setter(onMethod_ = @Autowired)
    private OpenLayerService openLayerService;

    public IndexController(GuestService guestService){
        this.guestService = guestService;
    }

    @GetMapping("/")
    public String index(HttpServletResponse res , HttpServletRequest req , Model model) {
        JSONArray jsonarr = new JSONArray();

        try{
            jsonarr = openLayerService.getAreaJsonData("test1");
            log.info("json->>" + jsonarr);
        } catch(Exception e) {
            log.error("index error -> " , e);
        }

        model.addAttribute("jsonarr" , jsonarr);
        return "index";
    }

    @GetMapping("/{id}")
    public Map<String, Object> viewGuest(@PathVariable("id") long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<Guest> oGuest = guestService.findById(id);
        if(oGuest.isPresent()) {
            response.put("result", "SUCCESS");
            response.put("user", oGuest.get());
        }

        return response;
    }

    @RequestMapping("/delete")
    public Map<String, Object> deleteGuest() {
        Map<String, Object> response = new HashMap<>();

        List<Guest> oGuests = guestService.findAll();
        for(Guest oGuest : oGuests) {
            response.put("result", "SUCCESS");
            response.put("user", oGuest);
        }

        return response;
    }

    @RequestMapping("/save")
    public Map<String, Object> saveGuest() {
        Map<String, Object> response = new HashMap<>();

        GuestDto guestDto = new GuestDto();
        guestDto.setName("test2");
        guestDto.setEmail("test2@test.com");
        guestDto.setPassword("test2");
        guestDto.setPhoneNumber("010-1234-1234");

        guestService.save(guestDto);

        response.put("result", "SUCCESS");
        response.put("user", "aa");

        return response;
    }
}
