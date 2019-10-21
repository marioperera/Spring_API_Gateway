package com.test.apigateway.filters;

import com.test.apigateway.Services.PartitionUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Component
@Configuration
public class Testfilter extends OncePerRequestFilter {

    @Autowired
    PartitionUrlService partitionUrlService;

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, IOException, ServletException {
//        System.out.println("yooooooooooooooooooooooo");
//        System.out.println(httpServletRequest.getMethod());
        if(httpServletRequest.getRequestURI().contains("/h2-console/")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
        if(httpServletRequest.getRequestURI().contains("api/addApi")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
        if(httpServletRequest.getRequestURI().contains("api/register")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
        if(httpServletRequest.getRequestURI().contains("query")){
            if(httpServletRequest.getMethod().equals("POST")){
                try {
                    partitionUrlService.capturePostParameters(httpServletRequest.getRequestURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(httpServletRequest.getMethod().equals("GET")){
                try {
                    partitionUrlService.captureGetParameters(httpServletRequest.getRequestURI(),httpServletRequest.getParameterMap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }




    // ..........
}