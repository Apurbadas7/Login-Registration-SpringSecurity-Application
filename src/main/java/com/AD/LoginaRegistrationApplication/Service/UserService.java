package com.AD.LoginaRegistrationApplication.Service;

import com.AD.LoginaRegistrationApplication.DTO.UserRequest;
import com.AD.LoginaRegistrationApplication.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    @Autowired


    public User saveUser(User user);
    public String login(UserRequest user);



}
