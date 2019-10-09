package com.test.apigateway.Repositories;

import com.test.apigateway.DAO.QueryEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryEndpointRepository extends JpaRepository<QueryEndpoint,Integer> {
}
