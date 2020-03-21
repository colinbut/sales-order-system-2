package com.mycompany.userservice.service;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import com.mycompany.userservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return UserPrincipal.create(user);
    }

    public void saveNewUser(String name, String username, String password, String email, List<String> roles){
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("Username " + username + " already exists!");
        }
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(String.join(",", roles));
        userRepository.save(user);
    }

}
