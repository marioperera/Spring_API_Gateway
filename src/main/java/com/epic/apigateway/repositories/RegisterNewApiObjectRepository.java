package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.RegisterNewApiObject;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface RegisterNewApiObjectRepository extends JpaRepository<RegisterNewApiObject ,Integer> {

    RegisterNewApiObject getByNewEndpoint(String newEndpoint);
}
