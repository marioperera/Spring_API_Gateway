package com.epic.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EntityScan("com.epic.apigateway.dao")
@EnableJpaRepositories(basePackages = "com.epic.apigateway.repositories")
@EnableMongoRepositories(basePackages = "com.epic.apigateway.mongo.mongorepository")
//@ComponentScan("com.epic.apigateway")
public class ApigatewayApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(ApigatewayApplication.class, args);

//        System.out.println("Scanned Components");
//        for (String name: applicationContext.getBeanDefinitionNames()) {
//
//            System.out.println(name);
//
//        }

    }

}
