package com.experis.springilmiofotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    // config avere un userDetailsService
    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // config per avere un passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // creo un DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // assegno userDetailsService
        provider.setUserDetailsService(userDetailsService());
        // assegno passwordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/categories").hasAuthority("ADMIN")
                .requestMatchers("/users").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/photos/**").hasAuthority("ADMIN")
                .requestMatchers("/photos", "/photos/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        // disabilita token sicurezza
        http.csrf().disable();
        return http.build();
    }


}
