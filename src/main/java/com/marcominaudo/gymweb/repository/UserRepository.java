package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.User;
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

    //@Query()
    //Boolean isPtOfCustomer(long customerId, long ptId);
    // TODO : creare questa query

}
