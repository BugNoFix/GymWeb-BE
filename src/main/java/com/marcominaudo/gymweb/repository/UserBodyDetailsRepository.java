package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.UserBodyDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBodyDetailsRepository extends JpaRepository<UserBodyDetails, Long> {

    Page<UserBodyDetails> findByUserId(long userId, Pageable page);
}
