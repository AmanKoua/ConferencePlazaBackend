package com.conferencePlaza.plaza.user;

import jakarta.persistence.*;

@Entity
@Table(name = "reviewAssignments")
public class ReviewerAssignment {

    @Id
    @SequenceGenerator(
            name = "review_assignment_sequence",
            sequenceName = "review_assignment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="review_assignment_sequence"
    )
    private long id;

    public Long reviewerId;
    public Long paperId;
    public String status;

    public ReviewerAssignment() {
    }

    public ReviewerAssignment(long id, Long reviewerId, Long paperId, String status) {
        this.id = id;
        this.reviewerId = reviewerId;
        this.paperId = paperId;
        this.status = status;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
