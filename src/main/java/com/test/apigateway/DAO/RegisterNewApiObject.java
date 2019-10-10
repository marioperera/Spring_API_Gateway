package com.test.apigateway.DAO;

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

    @Column(name = "API_ENDPOINT")
    private String new_endpoint;

    @Column(name = "REQUEST_TYPE")
    private String type;

    @OneToMany
    private List<QueryEndpoint> queryEndpoints;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNew_endpoint() {
        return new_endpoint;
    }

    public void setNew_endpoint(String new_endpoint) {
        this.new_endpoint = new_endpoint;
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
