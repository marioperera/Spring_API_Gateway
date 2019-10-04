package com.test.apigateway.DAO;

import java.util.HashMap;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
public class QueryEndpoint {
    private String endpoint;
    private List<RequestParameter> requestParameters;
    private String[] response_attribs;

    public QueryEndpoint(String s, List<RequestParameter> reqs) {
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

    public List<RequestParameter> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(List<RequestParameter> requestParameters) {
        this.requestParameters = requestParameters;
    }
}
