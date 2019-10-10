package com.test.apigateway.Repositories;

import com.test.apigateway.DAO.SaveNewApiObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaveNewApiObjRepository extends JpaRepository<SaveNewApiObj,Integer> {
	
	SaveNewApiObj findByUrlAndType(String url, String type);
}
