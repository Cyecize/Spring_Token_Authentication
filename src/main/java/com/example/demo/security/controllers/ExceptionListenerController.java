package com.example.demo.security.controllers;

import com.example.demo.constants.AppConstants;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionListenerController {

    @ExceptionHandler({AccessDeniedException.class})
    public Map<String, Object> accessDeniedHandlerAction(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        return new HashMap<>() {{
           put(AppConstants.RESPONSE_BODY_MESSAGE_KEY, "You are not allowed to visit this page!");
        }};
    }
}
