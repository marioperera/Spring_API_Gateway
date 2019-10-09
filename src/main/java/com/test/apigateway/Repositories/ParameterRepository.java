package com.test.apigateway.Repositories;

import com.test.apigateway.DAO.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter,Integer> {
}
