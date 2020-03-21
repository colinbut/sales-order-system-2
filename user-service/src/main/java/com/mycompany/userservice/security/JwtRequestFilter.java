package com.mycompany.userservice.security;

import com.mycompany.userservice.service.JwtUserDetailsService;
import com.mycompany.userservice.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = httpServletRequest.getHeader("Authorization");
        LOGGER.debug("RequestHeaderAuth: {}", requestHeader);
        String username = null;
        String jwtToken = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            jwtToken = requestHeader.replace("Bearer ", "");
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } else {
            LOGGER.warn("Couldn't find the bearer so will ignore the header: {}", requestHeader);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            boolean isTokenValid = jwtTokenUtil.validateToken(jwtToken, userDetails);
            if (isTokenValid) {

                Jws<Claims> claims = Jwts.parser()
                        .requireIssuer("Sales Order System")
                        .setSigningKey(secret)
                        .parseClaimsJws(jwtToken);

                String user = claims.getBody().get("usr", String.class);
                String roles = claims.getBody().get("rol", String.class);
                List<GrantedAuthority> grantedAuthorities = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                LOGGER.info("Authenticated User {} , setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                LOGGER.warn("Token: {} not valid for user: {}", jwtToken, userDetails);
            }
        } else {
            LOGGER.warn("Username: {} is null or Security Context has no Authentication set", username);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
