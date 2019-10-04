package com.test.apigateway.DAO;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
public class Responsebean {
    Object value;
    String code;
    String status;

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
