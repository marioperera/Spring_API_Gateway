package com.epic.apigateway.mongo.mongorepository;

import com.epic.apigateway.mongo.documents.SavenewApiDocument;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ApiDocumentRepository extends MongoRepository<SavenewApiDocument, String> {

    public SavenewApiDocument getDistinctByUrl(String url);
    
    public List<SavenewApiDocument> findByUrlLike(String Url);
}
