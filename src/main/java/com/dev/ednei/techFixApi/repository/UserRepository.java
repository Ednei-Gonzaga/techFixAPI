package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String username);
}
