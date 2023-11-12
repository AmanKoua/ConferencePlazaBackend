package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query("SELECT paper FROM Paper paper WHERE paper.conferenceId = :confId AND paper.authorId = :userId")
    List<Paper> getPapersByConferenceIdAndAuthorId(@Param("confId") Long confId, @Param("userId") Long userId);

    @Query("SELECT paper FROM Paper paper WHERE paper.conferenceId = :confId")
    List<Paper> getPapersByConferenceId(@Param("confId") Long confId);

}
