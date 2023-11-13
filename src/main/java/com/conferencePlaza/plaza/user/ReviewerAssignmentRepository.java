package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewerAssignmentRepository extends JpaRepository<ReviewerAssignment, Long> {

    @Query("SELECT r FROM ReviewerAssignment r WHERE r.paperId = :paperId")
    List<ReviewerAssignment> getReviewerAssignmentsByPaperId(@Param("paperId") Long paperId);

    @Query("SELECT r FROM ReviewerAssignment r WHERE r.reviewerId = :userId")
    List<ReviewerAssignment> getReviewerAssignmentsByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE ReviewerAssignment r SET r.status = :status WHERE r.paperId = :paperId AND r.reviewerId = :reviewerId")
    int setReviewerAssignment(@Param("paperId") Long paperId, @Param("reviewerId") Long reviewerId, @Param("status") String status);

}
