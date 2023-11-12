package com.conferencePlaza.plaza.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    // define additional queries to be executed here as necessary

}
