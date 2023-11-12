package com.conferencePlaza.plaza.user;

import jakarta.persistence.*;

@Entity
@Table(name="paperCoAuthors")
public class PaperCoAuthors {

    @Id
    @SequenceGenerator(
            name = "coauthor_sequence",
            sequenceName = "coauthor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator ="coauthor_sequence"
    )
    private long id;
    public Long paperId;
    public Long userId;

    public PaperCoAuthors() {
    }

    public PaperCoAuthors(Long paperId, Long userId) {
        this.paperId = paperId;
        this.userId = userId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
