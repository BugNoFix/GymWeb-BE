package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<User> user(){
        User user = userService.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bodyDetails")
    public ResponseEntity<List<UserBodyDetails>> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        List<UserBodyDetails> userBodyDetails = userService.getBodyDetails(page, size);
        return new ResponseEntity<>(userBodyDetails, HttpStatus.OK);
    }

    @PostMapping("/bodyDetails")
    public ResponseEntity<UserBodyDetails> bodyDetails(@RequestBody UserBodyDetails userBodyDetails){
        UserBodyDetails body = userService.setBodyDetails(userBodyDetails);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/bodyDetails/{uuid}") // accessibile al pt
    public ResponseEntity<List<UserBodyDetails>> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("uuid") String uuid) throws UserException, BodyDetailsException {
        List<UserBodyDetails> userBodyDetails = userService.getBodyDetailsOfCustomer(page, size, uuid);
        return new ResponseEntity<>(userBodyDetails, HttpStatus.OK);
    }

    @GetMapping("/privacy")
    public ResponseEntity<User> privacy(@RequestParam(name = "value", defaultValue = "false") boolean value){
        User body = userService.setPrivacy(value);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/update/{uuid}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("uuid") String uuid) {
        User userdb = userService.updateUser(user, uuid);
        return new ResponseEntity<>(userdb, HttpStatus.OK);
    }
}
