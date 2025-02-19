package com.AD.LoginaRegistrationApplication.configuration;


import com.AD.LoginaRegistrationApplication.Service.UserService;
import com.AD.LoginaRegistrationApplication.Service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserConfiguration {

    @Bean
    UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService getUserServiceDetails() {
        return new CustomUserService();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(getUserServiceDetails());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(getDaoAuthenticationProvider())
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/saveUser", "/").permitAll()
                        .anyRequest().authenticated()).

                        formLogin()
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                .defaultSuccessUrl("/profile")


                                .permitAll();


        return http.build();
    }
}

