package com.example.demo.security.repositories;

import com.example.demo.security.entities.AuthToken;
import com.example.demo.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {

    List<AuthToken> findByUser(User user);
}
