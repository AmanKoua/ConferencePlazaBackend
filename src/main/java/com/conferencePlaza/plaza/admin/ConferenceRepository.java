package com.conferencePlaza.plaza.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
    @Query("SELECT c FROM Conference c WHERE chairId = :chairId")
    Optional<Conference> getConferenceByChairId(@Param("chairId") Long chairId);
}
