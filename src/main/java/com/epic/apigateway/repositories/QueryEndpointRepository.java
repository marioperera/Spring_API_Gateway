package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.QueryEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface QueryEndpointRepository extends JpaRepository<QueryEndpoint,Integer> {
}
