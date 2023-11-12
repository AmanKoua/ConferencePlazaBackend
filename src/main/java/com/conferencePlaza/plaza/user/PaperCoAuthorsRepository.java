package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaperCoAuthorsRepository extends JpaRepository<PaperCoAuthors, Long> {

//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT authors FROM PaperCoAuthors authors where authors.paperId = :paperId")
    List<PaperCoAuthors> findCoAuthorsForSubmission(@Param("paperId") Long paperId);

}
