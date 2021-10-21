package com.kyr.springjpa.model.bean;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String conpany;

    @NotNull
    private Timestamp registerDate;
}
