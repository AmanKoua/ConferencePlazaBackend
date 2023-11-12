package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query("SELECT paper FROM Paper paper WHERE paper.conferenceId = :confId AND paper.authorId = :userId")
    List<Paper> getPapersByConferenceId(@Param("confId") Long confId, @Param("userId") Long userId);

}
