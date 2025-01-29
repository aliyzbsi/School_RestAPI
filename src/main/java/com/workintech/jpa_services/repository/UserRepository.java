package com.workintech.jpa_services.repository;

import com.workintech.jpa_services.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser,Long> {

    @Query("SELECT u FROM ApplicationUser u WHERE u.email=:email")
    Optional<ApplicationUser> findByUserEmail(String email);
}
