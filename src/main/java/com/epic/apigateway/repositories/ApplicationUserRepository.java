package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {

    @Query(value = "SELECT r FROM ApplicationUser  r WHERE r.username = ?1 AND r.isActive ='true'")
    ApplicationUser findByUsername(String userName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ApplicationUser u SET u.isActive=?1 WHERE u.username = ?2 ")
    int updateUserStatus(String status ,String userName);
}
