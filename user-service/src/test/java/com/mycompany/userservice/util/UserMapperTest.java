package com.mycompany.userservice.util;

import com.mycompany.userservice.dto.UserDto;
import com.mycompany.userservice.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void mapUserToUserDto() {
        User user = new User();
        user.setId(1);
        user.setName("Colin But");
        user.setUsername("colinbut");
        user.setPassword("password");
        user.setEmail("colin.but@email.com");
        user.setRoles("ADMIN,DEV,TEST");

        UserDto userDto = UserMapper.mapUserToUserDto(user);

        assertEquals("Colin But", userDto.getName());
        assertEquals("colinbut", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
        assertEquals("colin.but@email.com", userDto.getEmail());

        List<String> roles = userDto.getRoles();
        assertFalse(roles.isEmpty());
        assertEquals(3, roles.size());
        assertEquals("ADMIN", roles.get(0));
        assertEquals("DEV", roles.get(1));
        assertEquals("TEST", roles.get(2));
    }
}