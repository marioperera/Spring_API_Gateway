package com.test.apigateway.DAO;

import javax.persistence.*;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
@Entity
@Table(name = "PARAMETERS")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "PARAMETER_NAME")
    private String paramname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }
}
