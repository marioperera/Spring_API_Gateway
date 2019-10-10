package com.test.apigateway.DAO;

import javax.persistence.*;

/**
 * @author mario_p
 * @Date 10/8/2019
 */
@Entity
@Table(name = "REQUEST_ATTRIBUTES")
public class ResponseAttribute {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ATTRIBUTE_NAME")
    private String attribute;
    
    @Column(name = "TYPE")
    private String type;

    public ResponseAttribute(String s) {
        attribute =s;
    }

    public ResponseAttribute(String attribute, String type) {
		this.attribute = attribute;
		this.type = type;
	}
    
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
}
