package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.RegisterNewApiObject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@Repository
public interface RegisterNewApiObjectRepository extends JpaRepository<RegisterNewApiObject ,Integer> {

    RegisterNewApiObject getByNewEndpoint(String newEndpoint);
    RegisterNewApiObject findByNewEndpointAndType(String newEndpoint, String type);
    
    @Query("SELECT r FROM RegisterNewApiObject r WHERE r.newEndpoint LIKE ?1%")
    List<RegisterNewApiObject> findByLikeNewEndpointList(String newEndpoint);
    
    @Query("SELECT r FROM RegisterNewApiObject r WHERE r.newEndpoint LIKE ?1%")
    RegisterNewApiObject findByLikeNewEndpoint(String newEndpoint);
    
    @Query("SELECT DISTINCT r FROM RegisterNewApiObject r WHERE r.newEndpoint LIKE ?1% AND r.id != ?2")
    RegisterNewApiObject findByLikeNewEndpointAndId(String newEndpoint, int id);
}
