package com.mycompany.userservice.repository;

import com.mycompany.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername(){
        User user = userRepository.findByUsername("colinbut");
        assertEquals("colinbut", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Colin But", user.getName());
        assertEquals("colin.but@outlook.com", user.getEmail());
        assertEquals("ADMIN", user.getRoles());
    }
}
