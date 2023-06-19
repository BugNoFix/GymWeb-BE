package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.user.SearchUserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.user.SearchUserDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserMapper;
import com.marcominaudo.gymweb.controller.dto.user.UserRequestDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserResponseDTO;
import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterFormException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.security.controller.dto.RegisterResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import com.marcominaudo.gymweb.security.customAnnotation.CustomerAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyAdminAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyCustomerAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyPtAccess;
import com.marcominaudo.gymweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @OnlyAdminAccess
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) throws InvalidRegisterFormException {
        User user = userMapper.UserRequestDTOToUser(userRequestDTO);
        User userDB = userService.register(user);

        UserResponseDTO response = userMapper.UserToUserResponseDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get logged user data
    * */
    @FreeAccess
    @GetMapping
    public ResponseEntity<UserResponseDTO> user(){
        User user = userService.getUser();
        UserResponseDTO response = userMapper.UserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Get all user
     * */
    @OnlyAdminAccess
    @GetMapping("/all")
    public ResponseEntity<SearchUserDTO> users(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<User> users = userService.getAllUser(page, size);
        SearchUserDTO response = userMapper.listOfUsersToDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Get all pt
     * */
    @OnlyAdminAccess
    @GetMapping("/allPt")
    public ResponseEntity<SearchUserDTO> pts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<User> users = userService.getAllPt(page, size);
        SearchUserDTO response = userMapper.listOfUsersToDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get body details of logged user
    * */
    @OnlyCustomerAccess
    @GetMapping("/bodyDetails")
    public ResponseEntity<SearchUserBodyDetailsDTO> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<UserBodyDetails> userBodyDetails = userService.getBodyDetails(page, size);
        SearchUserBodyDetailsDTO response = userMapper.listOfBodyDetailsToDTO(userBodyDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Insert body details of logged user
    * */
    @OnlyCustomerAccess
    @PostMapping("/bodyDetails")
    public ResponseEntity<UserBodyDetailsDTO> bodyDetails(@RequestBody UserBodyDetailsDTO userBodyDetailsDTO){
        UserBodyDetails userBodyDetails = userMapper.DTOToBodyDetails(userBodyDetailsDTO);
        UserBodyDetails body = userService.setBodyDetails(userBodyDetails);
        UserBodyDetailsDTO response = userMapper.bodyDetailsToDTO(body);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get body details of specific user
    * */
    @OnlyPtAccess
    @GetMapping("/bodyDetails/{uuid}")
    public ResponseEntity<SearchUserBodyDetailsDTO> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("uuid") String uuid) throws UserException, BodyDetailsException {
        Page<UserBodyDetails> userBodyDetails = userService.getBodyDetailsOfCustomer(page, size, uuid);
        SearchUserBodyDetailsDTO response = userMapper.listOfBodyDetailsToDTO(userBodyDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Set and get privacy
    * */
    @OnlyCustomerAccess
    @GetMapping("/privacy")
    public ResponseEntity<UserResponseDTO> privacy(@RequestParam(name = "value", defaultValue = "false") boolean value){
        User user = userService.setPrivacy(value);
        UserResponseDTO response = userMapper.UserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Update specific user
    * */
    @OnlyAdminAccess
    @PostMapping("/update/{uuid}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO, @PathVariable("uuid") String uuid) throws UserException {
        User userRequest = userMapper.UserRequestDTOToUser(userRequestDTO);
        User userdb = userService.updateUser(userRequest, uuid, userRequestDTO.getUuidPt());
        UserResponseDTO response = userMapper.UserToUserResponseDTO(userdb);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CustomerAndPtAccess
    @GetMapping("/all/{uuidPT}")
    public ResponseEntity<SearchUserDTO> allUserOfPt( @PathVariable("uuidPT") String uuidPt,@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws UserException {
        Page<User> users = userService.allUserOfPt(uuidPt, page, size);
        SearchUserDTO response = userMapper.listOfUsersToDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: get customer with uuid and check id customer is pt's customer (Ptaccess)
    @OnlyPtAccess
    @GetMapping("/setPt/{uuidCustomer}")
    public ResponseEntity<UserResponseDTO> setPt( @PathVariable("uuidCustomer") String uuidCustomer) throws UserException {
        User user = userService.setPt(uuidCustomer);
        UserResponseDTO response = userMapper.UserToUserResponseDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
