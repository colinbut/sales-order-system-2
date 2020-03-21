package com.mycompany.userservice.controller;

import com.mycompany.userservice.controller.rest.SignupRequest;
import com.mycompany.userservice.dto.UserDto;
import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<UserDto> getUsersByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public void createNewUser(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());

        userRepository.save(user);
    }
}
