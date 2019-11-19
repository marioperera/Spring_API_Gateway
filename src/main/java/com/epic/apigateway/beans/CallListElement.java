package com.epic.apigateway.beans;

import java.util.HashMap;

/**
 * @author mario_p
 * @Date 10/9/2019
 */
public class CallListElement {
    String[] response_attribs;
    HashMap<String,String> mappings;

    public String[] getResponse_attribs() {
        return response_attribs;
    }

    public void setResponse_attribs(String[] response_attribs) {
        this.response_attribs = response_attribs;
    }

    public HashMap<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(HashMap<String, String> mappings) {
        this.mappings = mappings;
    }

    public CallListElement(String[] response_attribs, HashMap<String, String> mappings) {
        this.response_attribs = response_attribs;
        this.mappings = mappings;
    }
}
