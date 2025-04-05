package com.example.todoapp.service.implementation;

import com.example.todoapp.domain.model.User;
import com.example.todoapp.domain.model.UserPrinciple;
import com.example.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {

        User user = userRepository.findByFullName(fullName);

        if(user == null){
            System.out.println("User not found!!");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrinciple(user);
    }
}
