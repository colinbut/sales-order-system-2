package com.mycompany.userservice.service;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    public void givenUsername_whenLoadUserName_thenReturnCorrectUserDetails() {
        String username = "colinbut";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(createTestUser());

        UserDetails userPrincipal = jwtUserDetailsService.loadUserByUsername(username);

        assertEquals("colinbut", userPrincipal.getUsername());
        assertEquals("password", userPrincipal.getPassword());
        Collection<? extends GrantedAuthority> grantedAuthorities = userPrincipal.getAuthorities();
        assertFalse(grantedAuthorities.isEmpty());
        assertEquals(1, grantedAuthorities.size());
    }

    @Test
    public void shouldThrowExceptionWhenUnableToFindUserWithUsername(){
        String username = "colinbut";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username));
    }

    @Test
    public void givenNewUserInfoAndNoUserAlreadyExistsWithThatInfo_whenSaveNewUser_thenSave() {
        String username = "colinbut";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);

        jwtUserDetailsService.saveNewUser("Colin But", username, "password1", "email.com",
                Collections.singletonList("ADMIN"));

        Mockito.verify(userRepository, Mockito.times(1))
                .save(Mockito.any(User.class));
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExists_whenSaveNewUser() {
        String username = "colinbut";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(createTestUser());

        assertThrows(RuntimeException.class, () -> jwtUserDetailsService.saveNewUser("Colin But",
                username, "password1", "email.com", Collections.singletonList("ADMIN")));
    }

    private User createTestUser(){
        User user = new User();
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setRoles("ADMIN");
        return user;
    }
}
