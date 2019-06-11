package com.example.demo.security.controllers;

import com.example.demo.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class SecurityController {

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    @PostConstruct
    public void inPostConstruct() {
        System.out.println("FILTERS");
        FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
        List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
        list.stream()
                .flatMap(chain -> chain.getFilters().stream())
                .forEach(filter -> System.out.println(filter.getClass() + " "));
        System.out.println("!FILTERS");
    }

    @PostMapping("/authorize")
    @PreAuthorize("isFullyAuthenticated()")
    public Map<String, Object> authorizeAction(HttpSession session) {
        return new HashMap<>() {{
            put(AppConstants.AUTH_TOKEN_NAME, session.getAttribute(AppConstants.AUTH_TOKEN_NAME));
        }};
    }

    @GetMapping("/anon")
    @PreAuthorize("isAnonymous()")
    public Map<String, Object> anonAction() {
        return new HashMap<>() {{
            put(AppConstants.RESPONSE_BODY_MESSAGE_KEY, "You are anon!");
        }};
    }

    @GetMapping("/logged")
    @PreAuthorize("isFullyAuthenticated()")
    public Map<String, Object> loggedAction() {
        return new HashMap<>() {{
            put(AppConstants.RESPONSE_BODY_MESSAGE_KEY, "You are logged!");
        }};
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Map<String, Object> adminAction() {
        return new HashMap<>() {{
            put(AppConstants.RESPONSE_BODY_MESSAGE_KEY, "You are admin!");
        }};
    }
}
