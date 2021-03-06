package com.epic.apigateway.dao;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */

//@ElementCollection
//    List<Double> data;

@Entity
@Table(name = "ENPOINTS_FOR_REGISTER_NEW_APIS")
public class QueryEndpoint {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "API_ENDPOINTS")
    private String endpoint;
    @Column(name = "REQUEST_TYPE")
    private String type;

    @Transient
//    @OneToMany
    private List<Parameter> parameters;

    @Transient
    private HashMap<String,String> mappings;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "queryEndpoint", fetch = FetchType.EAGER)
//    private List<Mapping> mapping;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ResponseAttribute> response_attribs;

    public HashMap<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(HashMap<String, String> mappings) {
        this.mappings = mappings;
    }

    public QueryEndpoint() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResponseAttribute> getResponse_attribs() {
        return response_attribs;
    }

    public void setResponse_attribs(List<ResponseAttribute> response_attribs) {
        this.response_attribs = response_attribs;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

//	public List<Mapping> getMapping() {
//		return mapping;
//	}
//
//	public void setMapping(List<Mapping> mapping) {
//		this.mapping = mapping;
//	}
    
}
