package com.marcominaudo.gymweb.unitTest.repository;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilsTest utilsTest;

    @BeforeEach
    void init(){
        userRepository.deleteAll();
    }

    @Test
    void findCustomerById(){
        // Add data to h2 db
        User pt = utilsTest.getPt("Marco", "Minaudo", "marco@gmail.com");
        pt = userRepository.save(pt);
        User customer = utilsTest.getCustomer("Giovanni", "Milicia", "GioMil@gmail.com", true, pt);
        customer = userRepository.save(customer);

        // Test
        User result = userRepository.findCustomerById(customer.getId());
        assertEquals(customer, result);
    }
}
