package com.mycompany.userservice.controller;

import com.mycompany.userservice.controller.rest.LoginRequest;
import com.mycompany.userservice.controller.rest.JwtResponse;
import com.mycompany.userservice.controller.rest.SignupRequest;
import com.mycompany.userservice.exception.PasswordNotMatchException;
import com.mycompany.userservice.service.JwtUserDetailsService;
import com.mycompany.userservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new PasswordNotMatchException("Password doesn't match");
        }

        String jwtToken = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @PostMapping("/signup")
    public void createNewUser(@RequestBody @Valid SignupRequest signupRequest) {
        jwtUserDetailsService.saveNewUser(
                signupRequest.getName(),
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getRoles()
        );
    }

}
