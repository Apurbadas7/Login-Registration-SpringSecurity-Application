package com.AD.LoginaRegistrationApplication.Service;

import com.AD.LoginaRegistrationApplication.DTO.UserRequest;
import com.AD.LoginaRegistrationApplication.Entity.User;
import com.AD.LoginaRegistrationApplication.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserServiceImpl implements UserService{


    @Autowired
    private AuthenticationManager authenticationManager ;
   @Autowired
   private JwtService jwtService;

    @Override
    public String login(UserRequest user) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generatingToken(user.getUsername());
        }
        return null;
    }

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
