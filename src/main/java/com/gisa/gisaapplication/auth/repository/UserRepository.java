package com.gisa.gisaapplication.auth.repository;

import com.gisa.gisaapplication.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}