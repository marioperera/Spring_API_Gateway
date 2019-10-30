package com.epic.apigateway.repositories;

import com.epic.apigateway.dao.SaveNewApiObj;
import org.springframework.data.jpa.repository.JpaRepository;


//@Repository
public interface SaveNewApiObjRepository extends JpaRepository<SaveNewApiObj,Integer> {
	
	SaveNewApiObj findByUrlAndType(String url, String type);
	SaveNewApiObj findByUrl(String url);
}
