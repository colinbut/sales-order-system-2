package com.mycompany.customerservice.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

public class JwtAuthInfo {
    private String user;
    private List<GrantedAuthority> grantedAuthorities;
    private Date expiryDate;

    public JwtAuthInfo(String user, List<GrantedAuthority> grantedAuthorities, Date expiryDate) {
        this.user = user;
        this.grantedAuthorities = grantedAuthorities;
        this.expiryDate = expiryDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
