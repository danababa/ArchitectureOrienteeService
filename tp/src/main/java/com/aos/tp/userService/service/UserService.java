package com.aos.tp.userService.service;

import com.aos.tp.userService.config.JwtTokenProvider;
import com.aos.tp.userService.model.User;
import com.aos.tp.userService.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create user method
     * @param user model
     * @return added user
     */
    public User createUser (User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Authenticate User
     * @param username of user
     * @param password of user
     * @return generated token
     */
    public String authenticateUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtTokenProvider.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    /**
     * Get user by username
     * @param username of user
     * @return user
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    /**
     * Get user by id
     * @param id of user
     * @return user
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Delete user by id
     * @param id of user
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Update user
     * @param id of user
     * @param updatedUser body
     * @return updated user
     */
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        return userRepository.save(user);
    }
}
