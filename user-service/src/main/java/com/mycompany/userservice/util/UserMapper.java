package com.mycompany.userservice.util;

import com.mycompany.userservice.dto.UserDto;
import com.mycompany.userservice.model.User;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {}

    public static UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(Arrays.stream(user.getRoles().split(",")).collect(Collectors.toList()));
        return userDto;
    }
}
