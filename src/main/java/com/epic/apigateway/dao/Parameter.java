package com.epic.apigateway.dao;

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
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "MANDATORY")
    private Boolean mandatory;

    public Parameter() {
    }

    public Parameter(String name) {
        this.paramname =name;
    }

    public Parameter(String paramname, String type) {
		this.paramname = paramname;
		this.type = type;
	}
    
	public Parameter(String paramname, String type, Boolean mandatory) {
		this.paramname = paramname;
		this.type = type;
		this.mandatory = mandatory;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
    
}
