package com.aliw.pretemoica.dto;

import jakarta.validation.constraints.*;

public class UserDto {

    public String getEmail() {
        return email;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public String getPassword() {
        return password;
    }

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String email;

    // standard getters and setters
}