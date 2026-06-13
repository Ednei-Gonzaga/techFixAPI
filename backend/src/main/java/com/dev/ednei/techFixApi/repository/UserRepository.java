package com.dev.ednei.techFixApi.repository;

import com.dev.ednei.techFixApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);


    @Query("""
    SELECT u
        FROM User u
            WHERE u.id = :id 
       """)
    UserDetails findByIdForAuthentication(@Param("id") Long id);

    @Query("""
            SELECT u 
            FROM Users u
            JOIN u.employee e
            WHERE e.email = :emailEmployee    
            """)
    User findByEmailOfEmployee(@Param("emailEmployee") String emailEmployee);
}
