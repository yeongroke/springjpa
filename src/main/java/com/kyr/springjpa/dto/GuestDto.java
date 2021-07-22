package com.kyr.springjpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GuestDto {

    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private Date createTimestamp;
    private Date updateTimestamp;

}
