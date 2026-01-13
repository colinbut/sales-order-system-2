package com.mycompany.userservice.security;

import com.mycompany.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UserPrincipalTest {

    @Test
    public void testCreateNewUserPrincipalWithRoles() {
        User user = new User();
        user.setId(1);
        user.setName("Colin But");
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setEmail("colin.but@email.com");
        user.setRoles("ADMIN,DEV,TEST");

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        assertEquals("colinbut", userPrincipal.getUsername());
        assertEquals("password", userPrincipal.getPassword());
        Collection<? extends GrantedAuthority> grantedAuthorities = userPrincipal.getAuthorities();
        GrantedAuthority[] grantedAuthorities1 = grantedAuthorities.toArray(new GrantedAuthority[3]);
        assertEquals("ADMIN", grantedAuthorities1[0].getAuthority());
        assertEquals("DEV", grantedAuthorities1[1].getAuthority());
        assertEquals("TEST", grantedAuthorities1[2].getAuthority());
    }

    @Test
    public void testCreateNewUserPrincipalWithNoRoles() {
        User user = new User();
        user.setId(1);
        user.setName("Colin But");
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setEmail("colin.but@email.com");

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        assertEquals("colinbut", userPrincipal.getUsername());
        assertEquals("password", userPrincipal.getPassword());
        assertTrue(userPrincipal.getAuthorities().isEmpty());
    }
}