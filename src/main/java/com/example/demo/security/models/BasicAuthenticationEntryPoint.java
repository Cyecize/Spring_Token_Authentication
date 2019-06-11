package com.example.demo.security.models;

import com.example.demo.constants.AppConstants;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;

    public BasicAuthenticationEntryPoint(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("Content-Type", "application/json");

        response.addHeader("Access-Control-Allow-Headers", "session");
        response.addHeader("Access-Control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,PATCH");
        response.addHeader("Access-Control-Allow-Origin", "*");

        response.getWriter().println(this.gson.toJson(new HashMap<String, Object>() {{
                    put(AppConstants.RESPONSE_BODY_MESSAGE_KEY, "Invalid Credentials");
                }})
        );
    }
}
