package com.epic.apigateway.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/9/2019
 */
@Entity
@Table(name = "SAVED_NEW_APIS")
public class SaveNewApiObj {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "API_URL")
    private String url;

    public SaveNewApiObj() {

    }

    @Column(name = "REQUEST_TYPE")
    private String type;

    //fetch type
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<com.epic.apigateway.dao.Parameter> parameters;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ResponseAttribute> responseAttributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<com.epic.apigateway.dao.Parameter> getParameters() {
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
