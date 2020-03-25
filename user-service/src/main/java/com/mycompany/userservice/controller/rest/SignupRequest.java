package com.mycompany.userservice.controller.rest;

import javax.validation.constraints.*;
import java.util.List;

public class SignupRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 16)
    private String username;

    @Size(min = 8, max = 16)
    private String password;

    private List<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SignupRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
