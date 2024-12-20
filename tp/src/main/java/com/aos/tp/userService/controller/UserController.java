
package com.aos.tp.userService.controller;

import com.aos.tp.userService.model.User;
import com.aos.tp.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Login user
     * @return JWT access token
     */
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.authenticateUser(user.getUsername(),user.getPassword());
    }


    /**
     * Get user by id
     * @param id of user
     * @return user
     */
    @GetMapping("/user-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    /**
     * Update user by id
     * @param id of user
     * @param user user body
     * @return updated user
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    /**
     * Delete user by id
     * @param id of user
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



}

