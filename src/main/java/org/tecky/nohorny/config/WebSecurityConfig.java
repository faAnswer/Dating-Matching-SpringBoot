package org.tecky.nohorny.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("FilterChain");

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/nohorny/*").permitAll()
                .anyRequest().permitAll();

        return http.build();
    }
}
