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

}
