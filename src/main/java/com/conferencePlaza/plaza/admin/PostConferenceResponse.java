package com.conferencePlaza.plaza.admin;

public class PostConferenceResponse {

    public String message;

    public PostConferenceResponse() {
    }

    public PostConferenceResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
