package com.aos.tp.controller;

import com.aos.tp.model.User;
import com.aos.tp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user
     * @param user user body
     * @return new user
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok().body(userService.createUser(user));
    }

//    /**
//     * Login user
//     * @param userName of user
//     * @param password of user
//     * @return JWT access token
//     */
//    @PostMapping("/login")
//    public String login(@RequestBody @Valid String userName,@RequestBody @Valid  String password) {
//        return userService.authenticateUser(userName, password);
//    }
}
