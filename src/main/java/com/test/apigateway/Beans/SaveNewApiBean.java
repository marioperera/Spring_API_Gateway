package com.test.apigateway.Beans;

import java.util.HashMap;

/**
 * @author mario_p
 * @Date 10/9/2019
 */
public class SaveNewApiBean  {

    String endpoint;
    String type;
    HashMap<String,String> requestparams;
    HashMap<String,String> responseparams;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getRequestparams() {
        return requestparams;
    }

    public void setRequestparams(HashMap<String, String> requestparams) {
        this.requestparams = requestparams;
    }

    public HashMap<String, String> getResponseparams() {
        return responseparams;
    }

    public void setResponseparams(HashMap<String, String> responseparams) {
        this.responseparams = responseparams;
    }
}
