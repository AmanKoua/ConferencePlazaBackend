package com.conferencePlaza.plaza.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="conference")
public class Conference {

    @Id
    @SequenceGenerator(
            name = "conference_sequence",
            sequenceName = "conference_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "conference_sequence"
    )
    public long id;
    public String name;
    public String city;
    public String state;
    public String country;

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")

    public Date endDate;
    @JsonFormat(pattern="yyyy-MM-dd")

    public Date submissionDeadline;

    public Long chairId;

    public Conference() {
    }

    public Conference(String name, String city, String state, String country, Date startDate, Date endDate, Date submissionDeadline, Long chairId) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.submissionDeadline = submissionDeadline;
        this.chairId = chairId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(Date submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getChairId() {
        return chairId;
    }

    public void setChairId(Long chairId) {
        this.chairId = chairId;
    }
}
