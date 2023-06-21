package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.bodyDetails.BodyDetailsMapper;
import com.marcominaudo.gymweb.controller.dto.bodyDetails.SearchUserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.bodyDetails.UserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.user.SearchUserDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserMapper;
import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
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

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BodyDetailsMapper bodyDetailsMapper;

    @OnlyAdminAccess
    @PostMapping("/create")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) throws InvalidRegisterException {
        User user = userMapper.toUser(userDTO);
        User userDB = userService.register(user);

        UserDTO response = userMapper.toDTO(userDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get logged user data
    * */
    @FreeAccess
    @GetMapping
    public ResponseEntity<UserDTO> user(){
        User user = userService.getUser();
        UserDTO response = userMapper.toDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Get all user
     * */
    @OnlyAdminAccess
    @GetMapping("/all")
    public ResponseEntity<SearchUserDTO> users(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<User> users = userService.getAllUser(page, size);
        SearchUserDTO response = userMapper.toDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Get all pt
     * */
    @OnlyAdminAccess
    @GetMapping("/allPt")
    public ResponseEntity<SearchUserDTO> pts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<User> users = userService.getAllPt(page, size);
        SearchUserDTO response = userMapper.toDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get body details of logged user
    * */
    @OnlyCustomerAccess
    @GetMapping("/bodyDetails")
    public ResponseEntity<SearchUserBodyDetailsDTO> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        Page<UserBodyDetails> userBodyDetails = userService.getBodyDetails(page, size);
        SearchUserBodyDetailsDTO response = bodyDetailsMapper.toDTO(userBodyDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Insert body details of logged user
    * */
    @OnlyCustomerAccess
    @PostMapping("/bodyDetails")
    public ResponseEntity<UserBodyDetailsDTO> bodyDetails(@RequestBody UserBodyDetailsDTO userBodyDetailsDTO){
        UserBodyDetails userBodyDetails = bodyDetailsMapper.toBodyDetails(userBodyDetailsDTO);
        UserBodyDetails body = userService.setBodyDetails(userBodyDetails);
        UserBodyDetailsDTO response = bodyDetailsMapper.toDTO(body);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get body details of specific user
    * */
    @OnlyPtAccess
    @GetMapping("/bodyDetails/{uuid}")
    public ResponseEntity<SearchUserBodyDetailsDTO> bodyDetails(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size, @PathVariable("uuid") String uuid) throws UserException, BodyDetailsException {
        Page<UserBodyDetails> userBodyDetails = userService.getBodyDetailsOfCustomer(page, size, uuid);
        SearchUserBodyDetailsDTO response = bodyDetailsMapper.toDTO(userBodyDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Set and get privacy
    * */
    @OnlyCustomerAccess
    @GetMapping("/privacy")
    public ResponseEntity<UserDTO> privacy(@RequestParam(name = "value", defaultValue = "false") boolean value){
        User user = userService.setPrivacy(value);
        UserDTO response = userMapper.toDTO(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Update specific user
    * */
    @OnlyAdminAccess
    @PostMapping("/update/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("uuid") String uuid) throws UserException {
        User userRequest = userMapper.toUser(userDTO);
        User userdb = userService.updateUser(userRequest, uuid, userDTO.getUuidPt());
        UserDTO response = userMapper.toDTO(userdb);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CustomerAndPtAccess
    @GetMapping("/all/{uuidPT}")
    public ResponseEntity<SearchUserDTO> allUserOfPt( @PathVariable("uuidPT") String uuidPt,@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws UserException {
        Page<User> users = userService.allUserOfPt(uuidPt, page, size);
        SearchUserDTO response = userMapper.toDTO(users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
