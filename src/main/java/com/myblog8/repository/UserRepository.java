package com.myblog8.repository;

import com.myblog8.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //  custum query  //
    Optional<User> findByUsername(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String userName, String email); // can also be implemented with query annotation
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
