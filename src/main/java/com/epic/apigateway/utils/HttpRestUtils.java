package com.epic.apigateway.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpRestUtils {
    private static final RestTemplate restTemplate =new RestTemplate();

    private HttpRestUtils(){}

    public static RestTemplate GetInstance(){
        return restTemplate;
    }


}
