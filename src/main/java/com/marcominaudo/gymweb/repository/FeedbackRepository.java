package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findByUsersId(long id, Pageable pageSetting);
}
