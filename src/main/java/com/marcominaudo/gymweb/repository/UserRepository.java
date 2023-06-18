package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    @Query("SELECT u FROM User u WHERE u.id = :customerId")
    User findPtByCustomerId(long customerId);

    @Query("SELECT u FROM User u join fetch u.pt WHERE u.pt.id = :ptId") //TODO: da testare
    List<User> findCustomersByPtId(long ptId);

    Optional<User> findByUuid(String uuid);

    Page<User> findByRole(Role role, Pageable pageSetting);

    //@Query()
    //Boolean isPtOfCustomer(long customerId, long ptId);
    // TODO : creare questa query

}
