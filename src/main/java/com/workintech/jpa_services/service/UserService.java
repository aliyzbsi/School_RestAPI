package com.workintech.jpa_services.service;

import com.workintech.jpa_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Debug için log ekleyelim
        System.out.println("Attempting to load user: " + username);

        return userRepository.findByUserEmail(username)
                .orElseThrow(() -> {
                    System.out.println("User not found with email: " + username);
                    return new UsernameNotFoundException("User not found with email: " + username);
                });
    }
}
