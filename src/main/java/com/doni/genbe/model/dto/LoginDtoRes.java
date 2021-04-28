package com.doni.genbe.model.dto;

import com.doni.genbe.helper.AppUserType;
import lombok.Data;

@Data
public class LoginDtoRes {
    private String token;
    private String username;
    private AppUserType userType;
}
