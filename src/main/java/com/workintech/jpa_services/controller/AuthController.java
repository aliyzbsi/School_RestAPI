package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.RegisterUser;
import com.workintech.jpa_services.dto.UserResponse;
import com.workintech.jpa_services.service.AuthenticationService;
import com.workintech.jpa_services.user.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;
    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponse save(@RequestBody RegisterUser registerUser){
        System.out.println("burası çalıştı mı ? controller kısmı için ");
        ApplicationUser applicationUser=authenticationService.register(registerUser.fullName(),registerUser.email(),registerUser.password(),
                registerUser.role()

        );
        return new UserResponse(applicationUser.getId(),applicationUser.getFullName(),applicationUser.getEmail());
    }

}
