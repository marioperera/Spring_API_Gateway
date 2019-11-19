package com.epic.apigateway.dao;

import javax.persistence.*;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/9/2019
 */
@Entity
@Table(name = "REGISTERED_APIS")
public class RegisterNewApiObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "API_ENDPOINT",unique = true)
    private String newEndpoint;

    @Column(name = "REQUEST_TYPE")
    private String type;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<QueryEndpoint> queryEndpoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewEndpoint() {
        return newEndpoint;
    }

    public void setNewEndpoint(String newEndpoint) {
        this.newEndpoint = newEndpoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QueryEndpoint> getQueryEndpoints() {
        return queryEndpoints;
    }

    public void setQueryEndpoints(List<QueryEndpoint> queryEndpoints) {
        this.queryEndpoints = queryEndpoints;
    }
}
