package com.mycompany.customerservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = httpServletRequest.getHeader("Authorization");
        LOGGER.debug("RequestHeaderAuth: {}", requestHeader);
        String jwtToken = getJwtFromRequest(requestHeader);

        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            JwtAuthInfo authInfo = parseJwtClaims(jwtToken);
            boolean isTokenValid = validateToken(authInfo);
            String user = authInfo.getUser();
            if (isTokenValid) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, authInfo.getGrantedAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                LOGGER.debug("Authenticated User {} , setting security context", user);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                LOGGER.warn("Token: {} not valid for user: {}", jwtToken, user);
            }
        } else {
            LOGGER.warn("No JWT info in request or Security Context has no Authentication set");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean validateToken(JwtAuthInfo authInfo){
        return !authInfo.getExpiryDate().before(new Date());
    }

    private String getJwtFromRequest(String requestHeader) {
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            return requestHeader.replace("Bearer ", "");
        } else {
            LOGGER.warn("Couldn't find the bearer so will ignore the header: {}", requestHeader);
            return null;
        }
    }

    private JwtAuthInfo parseJwtClaims(String jwtToken) {
        Jws<Claims> claims = Jwts.parser()
                .requireIssuer(issuer)
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken);
        String user = claims.getBody().getSubject();
        List<LinkedHashMap<String, String>> roles = claims.getBody().get("scopes", ArrayList.class);

        List<GrantedAuthority> grantedAuthorities = roles == null || roles.isEmpty() ? Collections.emptyList()
                : roles.stream()
                .flatMap(x -> x.values().stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        LOGGER.debug("={}", grantedAuthorities);
        return new JwtAuthInfo(user, grantedAuthorities, claims.getBody().getExpiration());
    }
}
