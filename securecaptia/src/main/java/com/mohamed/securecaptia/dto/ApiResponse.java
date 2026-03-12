package com.mohamed.securecaptia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class ApiResponse <T> {
    private int status ;
    private String msg ;
    private T data ;
}
