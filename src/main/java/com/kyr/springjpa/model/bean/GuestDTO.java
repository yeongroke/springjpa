package com.kyr.springjpa.model.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {

    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    private Date createTimestamp;
    @NotNull
    private Date updateTimestamp;

}
