package com.epic.apigateway.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MAPPING")
public class Mapping {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	private String paramname;
	
	private String mappingname;
	
	@ManyToOne
	private QueryEndpoint queryEndpoint;

	public Mapping() {
		super();
	}

	public Mapping(String paramname, String mappingname) {
		super();
		this.paramname = paramname;
		this.mappingname = mappingname;
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

	public String getMappingname() {
		return mappingname;
	}

	public void setMappingname(String mappingname) {
		this.mappingname = mappingname;
	}

	public QueryEndpoint getQueryEndpoint() {
		return queryEndpoint;
	}

	public void setQueryEndpoint(QueryEndpoint queryEndpoint) {
		this.queryEndpoint = queryEndpoint;
	}
	
	
}
