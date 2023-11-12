package com.conferencePlaza.plaza.user;

import java.util.List;

public class PaperSlice {

    public String paperData;
    public String paperTitle;
    public List<String> coAuthors;
    public Long conferenceId;

    public PaperSlice() {
    }

    public PaperSlice(String paperData, String paperTitle, List<String> coAuthors, Long conferenceId) {
        this.paperData = paperData;
        this.paperTitle = paperTitle;
        this.coAuthors = coAuthors;
        this.conferenceId = conferenceId;
    }

    public String getPaperData() {
        return paperData;
    }

    public void setPaperData(String paperData) {
        this.paperData = paperData;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public List<String> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<String> coAuthors) {
        this.coAuthors = coAuthors;
    }
}
