package com.conferencePlaza.plaza.auth;

public class LoginRequestResponse {

    public String token;

    public String type;

    LoginRequestResponse(String token, String type){
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
