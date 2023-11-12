package com.conferencePlaza.plaza.auth;

public class LoginRequestBody {

    public String email;
    public String password;

    LoginRequestBody(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}