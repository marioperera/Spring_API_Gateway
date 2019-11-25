package com.epic.apigateway.beans;

import java.io.Serializable;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
public class Responsebean implements Serializable {
    Object value;
    String code;
    String status;

    public Responsebean(String status,String code) {
        this.code =code;
        this.status =status;
    }

    public Responsebean() {

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
