package com.kyr.springjpa.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationPattern {

    public static final Pattern NON_WORLD_CHARACTER;
    public static final Pattern NON_WORLD_CHARACTER_AND_KOREAN;

    static {
        NON_WORLD_CHARACTER = Pattern.compile("^\\w*$");
        NON_WORLD_CHARACTER_AND_KOREAN = Pattern.compile("^[a-zA-Z_0-9ㄱ-ㅎ가-힣]*$");
    }

}
