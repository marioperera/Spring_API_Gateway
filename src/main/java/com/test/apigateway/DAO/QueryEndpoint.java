package com.test.apigateway.DAO;

import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */

//@ElementCollection
//    List<Double> data;
public class QueryEndpoint {
    private String endpoint;
    private List<Parameter> parameters;
    private String[] response_attribs;

    public QueryEndpoint() {
    }

    public String[] getResponse_attribs() {
        return response_attribs;
    }

    public void setResponse_attribs(String[] response_attribs) {
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
}
