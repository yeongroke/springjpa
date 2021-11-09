package com.kyr.springjpa.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDTO<T> {
    private String apiKey;
    private T apiDTO;
}
