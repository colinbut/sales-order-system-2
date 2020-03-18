package com.mycompany.customerservice.security;

import com.mycompany.customerservice.service.JwtUserDetailsService;
import com.mycompany.customerservice.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
