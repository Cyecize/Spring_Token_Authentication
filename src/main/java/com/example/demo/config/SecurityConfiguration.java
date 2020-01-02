package com.example.demo.config;

import com.example.demo.security.filters.TokenAuthenticationFilter;
import com.example.demo.security.filters.TokenBasicAuthenticationFilter;
import com.example.demo.security.models.BasicAuthenticationEntryPoint;
import com.example.demo.security.services.AuthTokenService;
import com.example.demo.users.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final Gson gson;

    private final AuthTokenService authTokenService;

    @Autowired
    public SecurityConfiguration(UserService userService, Gson gson, AuthTokenService authTokenService) {
        this.userService = userService;
        this.gson = gson;
        this.authTokenService = authTokenService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .userDetailsService(this.userService)
                .httpBasic()
                    .authenticationEntryPoint(new BasicAuthenticationEntryPoint(this.gson))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        final TokenBasicAuthenticationFilter basicAuthenticationFilter = new TokenBasicAuthenticationFilter(this.authenticationManagerBean(), this.authTokenService);
        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(this.authTokenService);

        http.addFilter(basicAuthenticationFilter);
        http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
