package com.conferencePlaza.plaza.user;

import java.util.List;

public class GetAssignedPaper {

    private long paperId;
    public String paperData;
    public String paperTitle;
    public Long authorId;
    public String authorName;
    public List<String> coAuthorNames;
    public String status; // This is the final status, not the recommendation provided by the reviewer
    public Long conferenceId;

    public GetAssignedPaper() {
    }

    public GetAssignedPaper(long paperId, String paperData, String paperTitle, Long authorId, String authorName, List<String> coAuthorNames, String status, Long conferenceId) {
        this.paperId = paperId;
        this.paperData = paperData;
        this.paperTitle = paperTitle;
        this.authorId = authorId;
        this.authorName = authorName;
        this.coAuthorNames = coAuthorNames;
        this.status = status;
        this.conferenceId = conferenceId;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getCoAuthorNames() {
        return coAuthorNames;
    }

    public void setCoAuthorNames(List<String> coAuthorNames) {
        this.coAuthorNames = coAuthorNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
}

