package com.example.demo.security.filters;

import com.example.demo.constants.AppConstants;
import com.example.demo.security.entities.AuthToken;
import com.example.demo.security.services.AuthTokenService;
import com.example.demo.users.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private final AuthTokenService authTokenService;

    public TokenBasicAuthenticationFilter(AuthenticationManager authenticationManager, AuthTokenService authTokenService) {
        super(authenticationManager);
        this.authTokenService = authTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        //Get user.
        final User user = (User) authResult.getPrincipal();

        //Remove previous tokens.
        this.authTokenService.findByUser(user).forEach(this.authTokenService::remove);

        //Generate new token.
        final AuthToken newToken = this.authTokenService.createToken(user);

        //Add token to session.
        request.getSession(true).setAttribute(AppConstants.AUTH_TOKEN_NAME, newToken.getId());
    }
}
