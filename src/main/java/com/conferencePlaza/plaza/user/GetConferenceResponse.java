package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;

import java.util.List;

public class GetConferenceResponse {

    public Long paperId;
    public String title;
    public String author;
    public List<String> coAuthors;
    public String status;
    public GetConferenceResponse() {
    }

    public GetConferenceResponse(Long paperId, String title, String author, List<String> coAuthors, String status) {
        this.paperId = paperId;
        this.title = title;
        this.author = author;
        this.coAuthors = coAuthors;
        this.status = status;
    }

    public GetConferenceResponse(String title, String author, List<String> coAuthors, String status) {
        this.title = title;
        this.author = author;
        this.coAuthors = coAuthors;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<String> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
}
