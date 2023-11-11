package com.conferencePlaza.plaza.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Methods will be automatically be implemented because it's extending JpaRepository
    // Only certain custom methods and queries need to be implemented

}
