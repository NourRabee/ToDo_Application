package com.example.todoapp.config;

import com.example.todoapp.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enables Spring Security in the application and allows customization of security settings.
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; // Spring automatically injects your implementation "MyUserDetailsService".

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    // builder pattern
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http // for one object I am applying different settings.
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/signin", "/api/auth/signup")// These endpoints are public, meaning anyone can access them without authentication.
                        .permitAll()
                        .anyRequest().authenticated()) // All other requests must be authenticated." user-password Authentication "
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Every request must include authentication credentials (e.g., JWT token) because there is no session storage.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
//        UserDetailsService → Loads user details from the database
//        PasswordEncoder → Verifies hashed passwords
//        DaoAuthenticationProvider → Manages authentication "for database"
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder()); // Telling the DaoAuthenticationProvider which PasswordEncoder it should use to verify hashed passwords.
        provider.setUserDetailsService(userDetailsService);

        return provider;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

}
