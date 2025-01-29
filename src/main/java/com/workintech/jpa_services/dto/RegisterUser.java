package com.workintech.jpa_services.dto;

import com.workintech.jpa_services.user.Role;

public record RegisterUser(String fullName, String email, String password, Role role) {
}
