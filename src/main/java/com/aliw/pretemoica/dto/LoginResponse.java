package com.aliw.pretemoica.dto;

import java.util.List;

public class LoginResponse {

    private final String token;
    private final String tokenType;
    private final String email;
    private final List<String> roles;

    public LoginResponse(String token, String tokenType, String email, List<String> roles) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}

