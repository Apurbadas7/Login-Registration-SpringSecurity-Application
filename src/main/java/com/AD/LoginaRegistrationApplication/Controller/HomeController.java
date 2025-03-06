package com.AD.LoginaRegistrationApplication.Controller;

import com.AD.LoginaRegistrationApplication.DTO.UserRequest;
import com.AD.LoginaRegistrationApplication.DTO.UserResponse;
import com.AD.LoginaRegistrationApplication.Entity.User;
import com.AD.LoginaRegistrationApplication.Repository.UserRepo;
import com.AD.LoginaRegistrationApplication.Service.MailService;
import com.AD.LoginaRegistrationApplication.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


//@RestController
@Controller

public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;



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
    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest){
        String token=userService.login(userRequest);
        if(token==null){
           return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);
        }
        UserResponse userResponse=new UserResponse(userRequest.getUsername(),token);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @RequestMapping("/forgot")
    public String ForgotPasssword(){
        return "forgot_pwd1";
    }

    @Autowired
    private MailService mailService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/send_otp")
    public String SendOtp(@RequestParam("email")String email,HttpSession session){
        Random random=new Random();

        System.out.println("Email:"+email);



        //Generating otp
       int otp= 1000+random.nextInt(9999);
        System.out.println("OTP:"+otp);


        String subject="OTP is ";
        String message="OTP is: "+otp;
        String recipient=email;

        boolean f=this.mailService.sendMail(recipient,message,subject);

        if(f){
            session.setAttribute("myotp", otp);
            session.setAttribute("email", email);
            return "send_otp";
        }else{
            session.setAttribute("message", "Failed to send OTP.");
            return "forgot_password";
        }







    }
    @PostMapping("/verifyOTP")
    public String verifyOtp(@RequestParam("otp") Integer otp, HttpSession session) {
        Integer myOtp = (Integer) session.getAttribute("myotp");
        String email = (String) session.getAttribute("email");


        System.out.println("Stored OTP: " + myOtp + ", Entered OTP: " + otp);
        System.out.println("Stored Email: " + email);



        if (myOtp.equals(otp)) {

            return "password_change";
        } else {
            session.setAttribute("message","Wrong OTP!! Please Try again");
            return "send_otp";
        }
    }
    @PostMapping("/password_change")
    public String changePassword(@RequestParam("password") String password,@RequestParam("ConfirmPassword")String ConfirmPassword,HttpSession session){
        if(!password.equals(ConfirmPassword)){
            session.setAttribute("message","Password doesn't match");
            return "password_change";
        }
        String email=(String)session.getAttribute("email");

        User user=userRepo.findByEmail(email);
        if(user!=null){
            user.setPassword(passwordEncoder.encode(ConfirmPassword));
            userRepo.save(user);
            return "redirect:/login";
        }
        else {
            session.setAttribute("message","User not Found..");
            return "forgot_pwd1";
        }
    }






}
