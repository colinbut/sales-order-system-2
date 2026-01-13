package com.mycompany.userservice.controller;

import com.mycompany.userservice.dto.UserDto;
import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import com.mycompany.userservice.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/list")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUsersByUsername(@RequestParam(value = "username") String username) {
        User user = userRepository.findByUsername(username);
        UserDto userDto = UserMapper.mapUserToUserDto(user);
        return ResponseEntity.ok(userDto);
    }
}
