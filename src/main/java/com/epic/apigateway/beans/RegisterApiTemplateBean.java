package com.epic.apigateway.beans;

import java.util.HashMap;

/**
 * @author mario_p
 * @Date 10/8/2019
 */
public class RegisterApiTemplateBean {
    String endpoint;
    String type;
//    HashMap<Integer ,HashMap<QueryEndpoint,String[]>> call_list;
    HashMap<String ,String[]> call_list;

    public HashMap<String, String[]> getCall_list() {
        return call_list;
    }

    public void setCall_list(HashMap<String, String[]> call_list) {
        this.call_list = call_list;
    }

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


}
