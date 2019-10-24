package com.epic.apigateway.mongo.documents;
import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.ResponseAttribute;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.List;


@Document(collection = "SavedApis")
//@Entity
public class SavenewApiDocument {

    @Id
    private String id;

    private String url;

    private String type;

    private List<Parameter> parameters;


    private List<ResponseAttribute> responseAttributes;


//    public SavenewApiDocument(){
//
//    }
//
//    public SavenewApiDocument(String id, String url, String type, List<Parameter> parameters, List<ResponseAttribute> responseAttributes) {
//        this.id = id;
//        this.url = url;
//        this.type = type;
//        this.parameters = parameters;
//        this.responseAttributes = responseAttributes;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<ResponseAttribute> getResponseAttributes() {
        return responseAttributes;
    }

    public void setResponseAttributes(List<ResponseAttribute> responseAttributes) {
        this.responseAttributes = responseAttributes;
    }
}
