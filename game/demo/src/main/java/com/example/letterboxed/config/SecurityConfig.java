package com.example.letterboxed.config;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * We override configure method for http requests so we are able to process get and post requests from the frontend 
     * on the local machine.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource((CorsConfigurationSource) new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.addAllowedOrigin("*");
                        config.setAllowCredentials(false);
                        return config;
                    }
                })
                .and()
                .csrf().disable();
    }

}