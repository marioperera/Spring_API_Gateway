package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.ResponseAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface ResponseAttributeRepository extends JpaRepository<ResponseAttribute,Integer> {
}
