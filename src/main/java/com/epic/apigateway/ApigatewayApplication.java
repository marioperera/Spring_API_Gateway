package com.epic.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EntityScan("com.epic.apigateway.dao")
@EnableJpaRepositories(basePackages = "com.epic.apigateway.repositories")
@EnableMongoRepositories(basePackages = "com.epic.apigateway.mongo.mongorepository")
@ComponentScan("com.epic.apigateway.mongo.documents")
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

}
