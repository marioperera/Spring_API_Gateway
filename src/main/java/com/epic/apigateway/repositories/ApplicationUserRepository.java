package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findByUsername(String username);
}
