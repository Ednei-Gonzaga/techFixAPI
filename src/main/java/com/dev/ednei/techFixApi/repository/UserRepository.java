package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
