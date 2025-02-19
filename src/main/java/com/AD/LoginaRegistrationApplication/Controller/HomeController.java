package com.AD.LoginaRegistrationApplication.Controller;

import com.AD.LoginaRegistrationApplication.Entity.User;
import com.AD.LoginaRegistrationApplication.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller

public class HomeController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/demo")
    public String base(){
        return "demo";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user, HttpSession session){
        User u1= userService.saveUser(user);

        if(u1!=null){
            System.out.println("Register Successful!!");
        }
        else {
            System.out.println("Register Unsuccessfull!!");
        }
        return "redirect:/login";

    }




}
