package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    @Query("SELECT u FROM User u WHERE u.id = :customerId")
    User findCustomerById(long customerId);

    @Query(value = "SELECT u.* FROM User u inner join User pt on pt.id = u.pt_id WHERE pt.id = :ptId", nativeQuery = true)
    Page<User> findCustomersByPtId(long ptId, Pageable pageSetting);

    Optional<User> findByUuid(String uuid);

    Page<User> findByRole(Role role, Pageable pageSetting);

}
