package com.workintech.jpa_services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.SecureRandom;

@Configuration
public class SecurityConfig {

    //Bcrypt
    //Scrypt
    //Argon2
    //Bu kısım password ü encode etmesi için
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //Bu kısım login i database üzerinden yapması için
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    //Bu kısım security nin kalbi
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/api/**").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/student/**").permitAll();
                    auth.requestMatchers("/instructor/**").hasAnyAuthority("INSTRUCTOR");
                    auth.anyRequest().authenticated();
                }).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}

//permitAll yapılanlar giriş yapmadan da istek atılabilecek yerler
//authenticated yapılanlar sadece yetki verildiğinde giriş yapıldığında istek atılabilecek e