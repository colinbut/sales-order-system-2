package com.mycompany.userservice.config;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test") //don't execute this bean when run in tests (done using a 'test' profile)
public class ApplicationStartupListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupListener.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        LOGGER.info("Application Started! - Populating embedded db H2 with some sample users");
        setupSomePreExistingUserData();
    }

    private void setupSomePreExistingUserData(){
        List<User> users = userRepository.saveAll(Arrays.asList(
                createUser("Colin But", "colinbut", "password1", "email1@email.com", "ADMIN,DEV,TEST,ARCH"),
                createUser("Mark Sutton", "marksutton", "password2", "email2@email.com", "DEV,TEST,ARCH"),
                createUser("Emma Wilson", "emmawilson", "password3", "email3@email.com", "TEST"),
                createUser("Anton Murphy", "antonmurphy", "password4", "email4@email.com", "DEV,TEST"),
                createUser("Rebeckah Van Den Sar", "rebeckahv", "password5", "emai5l@email.com", "DEV,TEST,ARCH")
        ));
        LOGGER.info("Inserted following sample users: {}", users);
    }

    private User createUser(String name, String username, String password, String email, String roles) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(roles);
        return user;
    }
}
