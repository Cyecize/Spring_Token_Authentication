package com.example.demo.security.services;

import com.example.demo.security.entities.AuthToken;
import com.example.demo.users.entities.User;

import java.util.List;

public interface AuthTokenService {

    void update(AuthToken token);

    void remove(AuthToken authToken);

    boolean isAuthTokenExpired(AuthToken token);

    AuthToken createToken(User loggedInUser);

    AuthToken findById(String id);

    List<AuthToken> findByUser(User user);
}
