package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper, Long> {

    @Query("SELECT paper FROM Paper paper WHERE paper.conferenceId = :confId AND paper.authorId = :userId")
    List<Paper> getPapersByConferenceIdAndAuthorId(@Param("confId") Long confId, @Param("userId") Long userId);

    @Query("SELECT paper FROM Paper paper WHERE paper.conferenceId = :confId")
    List<Paper> getPapersByConferenceId(@Param("confId") Long confId);

//    @Query("SELECT * FROM Paper p, ReviewerAssignment a WHERE a.reviewerId = :reviewerId AND a.paperId = p.id")
    @Query("SELECT p FROM Paper p JOIN ReviewerAssignment a ON a.paperId = p.id WHERE a.reviewerId = :reviewerId")
    List<Paper> getAssignedPapers(@Param("reviewerId") Long reviewerId);

    @Modifying
    @Transactional
    @Query("UPDATE Paper p SET p.status = :status WHERE p.id = :paperId")
    void setPaperDecision(@Param("paperId") Long paperId, @Param("status") String status);

}
