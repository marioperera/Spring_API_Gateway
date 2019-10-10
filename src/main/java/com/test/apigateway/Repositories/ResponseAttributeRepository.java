package com.test.apigateway.Repositories;

import com.test.apigateway.DAO.ResponseAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseAttributeRepository extends JpaRepository<ResponseAttribute,Integer> {
}
