package org.tecky.nohorny.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.tecky.nohorny.security.filter.JwtRequestFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig {

    @Autowired
    NoHornyUserDetailService noHornyUserDetailService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;
    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(noHornyUserDetailService);

        return new ProviderManager(provider);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("FilterChain");

        http
                .cors().and()
                .csrf()
                    .disable()
                .authorizeRequests()
//                    .antMatchers("/chatroom").authenticated()
//                    .antMatchers("/chatroom.html").authenticated()
//                    .antMatchers("/profile").authenticated()

                    .antMatchers("/nohorny/**").permitAll()
                    .antMatchers("/hello").authenticated()
                    .antMatchers("/api/user/register").permitAll()
                    .antMatchers("/api/user/login").permitAll()
                    .antMatchers("/api/match/result").authenticated()

                    .antMatchers("/index").permitAll()
                    .antMatchers("/*.css").permitAll()
                    .antMatchers("/**/*.css").permitAll()
                    .antMatchers("/*.js").permitAll()
                    .antMatchers("/**/*.js").permitAll()
                    .antMatchers("/index.html").permitAll()
                    .antMatchers("/login.html").permitAll();
//                    .anyRequest().permitAll();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");
        //http.formLogin().loginPage("/login");

        http.headers().frameOptions().disable();

        return http.build();
    }

}
