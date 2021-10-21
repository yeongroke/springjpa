package com.kyr.springjpa.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kyr.springjpa.util.ValidationPattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Member")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column
    private String password;

    @Column(nullable = false, length = 100)
    private String conpany;

    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Timestamp registerDate;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Board> boardlist = new ArrayList<>();

    @Builder
    public Member( String username , String email , String password , String conpany){
        this.username = username;
        this.email = email;
        this.password = password;
        this.conpany = conpany;
    }

    @Transient
    @JsonIgnore
    private static final int MAX_NAME_LENGTH = 15;

    @Transient
    @JsonIgnore
    private static final int MIN_NAME_LENGTH = 5;

    @Transient
    @JsonIgnore
    private static final int MAX_PASSWORD_LENGTH = 21;

    @Transient
    @JsonIgnore
    private static final int MIN_PASSWORD_LENGTH = 5;

    public boolean isValid() {

        return isValidName(this.username) &&
                isValidPassword(this.password);
    }

    public boolean isValidName(String username) {

        int nameLength = username.length();

        return nameLength >= MIN_NAME_LENGTH &&
                nameLength <= MAX_NAME_LENGTH &&
                ValidationPattern.NON_WORLD_CHARACTER.matcher(username).matches();
    }

    public boolean isValidPassword(String password) {

        int passwordLength = password.length();

        return passwordLength >= MIN_PASSWORD_LENGTH &&
                passwordLength <= MAX_PASSWORD_LENGTH;
    }
}
