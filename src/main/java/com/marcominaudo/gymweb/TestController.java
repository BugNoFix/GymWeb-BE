package com.marcominaudo.gymweb;

import com.marcominaudo.gymweb.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/ciao")
    public ResponseEntity<User> ciao(){
         User a = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
