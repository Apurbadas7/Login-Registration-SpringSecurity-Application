package com.AD.LoginaRegistrationApplication.Service;

import com.AD.LoginaRegistrationApplication.Entity.User;
import com.AD.LoginaRegistrationApplication.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        return userRepo.save(user);
    }


}
