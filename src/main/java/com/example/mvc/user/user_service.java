package com.example.mvc.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class user_service {
    
    @Autowired
    private user_repository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean login_session(user user) {
        Optional<user> login_user = repository.findByName(user.getName());
        String hash_password = login_user.get().getPassword();
        if(!login_user.isPresent()) {
            return false;
        }
        System.out.println(login_user.get().getPassword());
        if(passwordEncoder.matches(user.getPassword(), hash_password)) {
            return true;
        }
        return false;
    }

    public Boolean create_user(user user) {
        Optional<user> check = repository.findByName(user.getName());
        if(!check.isPresent() && !user.getName().equals("null")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            repository.insert(user);
            return true;
        }
        return false;
    }

    public Boolean login_jwt(user user) {
        Optional<user> login_user = repository.findByName(user.getName());
        String hash_password = login_user.get().getPassword();
        if(!login_user.isPresent()) {
            return false;
        }
        System.out.println(login_user.get().getPassword());
        if(passwordEncoder.matches(user.getPassword(), hash_password)) {
            return true;
        }
        return false;
    }

    public Optional<user> getUserByName(String name) {
        Optional<user> login_user = repository.findByName(name);
        return login_user;
    }
}
