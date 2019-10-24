package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface ParameterRepository extends JpaRepository<Parameter,Integer> {
}
