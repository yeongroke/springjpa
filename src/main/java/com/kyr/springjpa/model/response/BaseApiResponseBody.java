package com.kyr.springjpa.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "API_Response_Message" , description = "Base Api Response")
public class BaseApiResponseBody<T> implements Serializable {
    private static final long serialVersionUID = -8981146499042811408L;

    @ApiModelProperty(required = true)
    private Integer code;

    @ApiModelProperty(required = true)
    private String message;

    @ApiModelProperty(required = true)
    private T data;
}
