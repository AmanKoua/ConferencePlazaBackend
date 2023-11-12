package com.conferencePlaza.plaza.user;

import jakarta.persistence.*;

@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @SequenceGenerator(
            name = "paper_sequence",
            sequenceName = "paper_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="paper_sequence"
    )
    private long id;

    public String paperData;
    public String paperTitle;
    public Long authorId;
    public String status;
    public Long conferenceId;

    public Paper() {
    }

    public Paper(String paperData, String paperTitle, Long authorId, String status, Long conferenceId) {
        this.paperData = paperData;
        this.paperTitle = paperTitle;
        this.authorId = authorId;
        this.status = status;
        this.conferenceId = conferenceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
