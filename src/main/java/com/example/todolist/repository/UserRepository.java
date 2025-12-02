package com.example.todolist.repository;

import com.example.todolist.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
}