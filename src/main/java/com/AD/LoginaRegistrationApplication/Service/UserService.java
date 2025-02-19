package com.AD.LoginaRegistrationApplication.Service;

import com.AD.LoginaRegistrationApplication.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User saveUser(User user);


}
