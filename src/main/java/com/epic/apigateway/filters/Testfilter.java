package com.epic.apigateway.filters;

import com.epic.apigateway.services.PartitionUrlService;
import com.epic.apigateway.utils.WebUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
public class Testfilter extends OncePerRequestFilter {

    @Autowired
    PartitionUrlService partitionUrlService;

    @Autowired
    WebUtils webUtils;

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
//        query results will be written as a hashmap

        HashMap<String,String> resultmap =new HashMap<>();

        if(httpServletRequest.getRequestURI().contains("query")){
            if(httpServletRequest.getMethod().equals("POST")){
                try {
                    partitionUrlService.capturePostParameters(httpServletRequest.getRequestURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(httpServletRequest.getMethod().equals("GET")){
                try {
                    HashMap<String,String> headerdetails = (HashMap<String, String>) webUtils.getHeadersInfo(httpServletRequest);
                    resultmap =partitionUrlService.captureGetParameters(httpServletRequest.getRequestURI(),httpServletRequest.getParameterMap(),headerdetails);
                    httpServletResponse.setContentType("Application/Json");
                    ObjectMapper mapper = new ObjectMapper();
                    String json = "";
                    try {
                        json = mapper.writeValueAsString(resultmap);
//                        System.out.println("ResultingJSONstring = " + json);
                        //System.out.println(json);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    httpServletResponse.getWriter().write(json);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }




    // ..........
}