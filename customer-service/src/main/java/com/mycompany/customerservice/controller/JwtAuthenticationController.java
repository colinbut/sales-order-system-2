package com.mycompany.customerservice.controller;

import com.mycompany.customerservice.service.JwtUserDetailsService;
import com.mycompany.customerservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody JwtRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                ));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String jwtToken = jwtTokenUtil.generateToken(userDetails, authenticationRequest.getRoles());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

}
