package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;

import java.util.List;

public class GetConferencesResponse {

    public List<Conference> conferences;

    public GetConferencesResponse() {
    }

    public GetConferencesResponse(List<Conference> conferences) {
        this.conferences = conferences;
    }

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }

}
