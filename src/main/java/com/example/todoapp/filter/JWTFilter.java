package com.example.todoapp.filter;

import com.example.todoapp.service.implementation.JWTService;
import com.example.todoapp.service.implementation.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context; //Using ApplicationContext allows for lazy loading or on-demand resolution of the
    // MyUserDetailsService bean. The JwtFilter only needs MyUserDetailsService when validating the
    // token (i.e., during the HTTP request processing). This could potentially save resources if the
    // MyUserDetailsService isn't needed immediately at the start of the filter processing.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Bearer header.payload.secretKey
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
        }

        // SecurityContextHolder is a central component in Spring Security that holds the security context of the current
        // execution thread (i.e., the current request or session). It stores the authentication information for the current
        // user and makes it accessible throughout the application.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // SecurityContextHolder.getContext().getAuthentication() == null
            // If it's null, that means the user is not yet authenticated in the current security context.
            // This condition ensures that we don't reauthenticate the user if they have already been authenticated
            // in the current session.

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
            // This line essentially loads the user details (like the user’s authorities and credentials) based on the
            // email that was extracted from the JWT token.

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities()); // This creates an instance of the
                // UsernamePasswordAuthenticationToken, which is a Spring Security class that represents an authenticated user.
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request)); // This line is used to add additional web-related details to the authentication token.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
