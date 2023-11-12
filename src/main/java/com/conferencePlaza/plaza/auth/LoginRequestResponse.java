package com.conferencePlaza.plaza.auth;

public class LoginRequestResponse {

    public String token;

    LoginRequestResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
