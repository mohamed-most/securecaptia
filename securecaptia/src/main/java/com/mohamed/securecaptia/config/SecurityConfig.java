package com.mohamed.securecaptia.config;

import com.mohamed.securecaptia.handler.CustomAccessDeniedHandler;
import com.mohamed.securecaptia.handler.CustomAuthenticationEntryPointEx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(cust ->cust.disable())
                .cors(cust->cust.disable()) // for dev but in production we will configure it
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(req ->
                req.requestMatchers("/api/auth/**").permitAll()
                );
        httpSecurity.exceptionHandling(ex->
                ex.authenticationEntryPoint(new CustomAuthenticationEntryPointEx())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
        );
        httpSecurity.authorizeHttpRequests(req -> req.anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider ;

    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
