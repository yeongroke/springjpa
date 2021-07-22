package com.kyr.springjpa.controller;

import com.kyr.springjpa.dto.GuestDto;
import com.kyr.springjpa.entity.Guest;
import com.kyr.springjpa.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class IndexController {

    @Value("${spring.profiles.active}")
    private String profileActive;

    @Autowired
    private GuestService guestService;

    public IndexController(GuestService guestService){
        this.guestService = guestService;
    }

    @GetMapping("/")
    public String index() {
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
