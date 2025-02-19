package com.AD.LoginaRegistrationApplication.Repository;

import com.AD.LoginaRegistrationApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String username);
}
