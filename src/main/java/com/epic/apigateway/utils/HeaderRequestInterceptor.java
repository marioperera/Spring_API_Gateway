package com.epic.apigateway.utils;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashMap;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    public HashMap<String,String> headers;

    public HeaderRequestInterceptor(HashMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        this.headers.forEach((t,k) ->{
            httpRequest.getHeaders().set(t,k);
        });

//        todo : currently only supports http so jsonplaceholder.com dose not work
        System.out.println("restemplate request to url intercepted");
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
