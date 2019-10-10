package com.test.apigateway.Repositories;

import com.test.apigateway.DAO.RegisterNewApiObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterNewApiObjectRepository extends JpaRepository<RegisterNewApiObject ,Integer> {
}
