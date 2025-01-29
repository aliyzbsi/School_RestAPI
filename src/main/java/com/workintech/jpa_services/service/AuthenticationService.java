package com.workintech.jpa_services.service;

import com.workintech.jpa_services.repository.RoleRepository;
import com.workintech.jpa_services.repository.UserRepository;
import com.workintech.jpa_services.user.ApplicationUser;
import com.workintech.jpa_services.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser register(String fullName, String email, String password,Role requestRole) {
        // Email kontrolü
        if (userRepository.findByUserEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists: " + email);
        }

        // Debug için log
        System.out.println("Registering new user with email: " + email);

        String encodedPassword = passwordEncoder.encode(password);

        Set<Role> roles = new HashSet<>();
        if (requestRole == null) {
            // Eğer role null ise default olarak USER rolü ata
            Role userRole = roleRepository.findByAuthority("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found"));
            roles.add(userRole);
        } else {
            // Eğer role null değilse, gelen role'ü veritabanından kontrol et
            Role role = roleRepository.findByAuthority(requestRole.getAuthority())
                    .orElseThrow(() -> new RuntimeException("Requested role not found: " + requestRole.getAuthority()));
            roles.add(role);
        }

        ApplicationUser user = new ApplicationUser();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setAuthorities(roles);

        // Debug için log
        System.out.println("Saving user to database...");

        return userRepository.save(user);
    }


}
