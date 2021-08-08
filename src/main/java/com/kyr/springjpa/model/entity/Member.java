package com.kyr.springjpa.model.entity;

import com.kyr.springjpa.model.bean.MemberDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Setter
    @Column(nullable = false, length = 50)
    private String username;

    @Setter
    @Column(nullable = false, length = 100)
    private String email;

    @Setter
    @Column(nullable = false, length = 100)
    private String conpany;

    @Builder
    public Member( String email , String username , String conpany){
        this.email = email;
        this.username = username;
        this.conpany = conpany;
    }
}
