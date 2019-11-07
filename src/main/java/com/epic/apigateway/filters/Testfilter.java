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
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.epic.apigateway.services.TestService;


@Component
@Configuration
public class Testfilter extends OncePerRequestFilter {

    @Autowired
    PartitionUrlService partitionUrlService;

    @Autowired
	TestService testService;

    @Autowired
    WebUtils webUtils;

    @Override
    protected void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, IOException, ServletException {


    	System.out.println("This is from fliter");
        if(httpServletRequest.getRequestURI().contains("api/addApi")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
        if(httpServletRequest.getRequestURI().contains("api/register")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }
//        query results will be written as a hashmap

        HashMap<String,String> resultmap =new HashMap<>();

        if(httpServletRequest.getRequestURI().equalsIgnoreCase("/query/")|| httpServletRequest.getRequestURI().contains("/query/")){
//            check wether the request method is GET or POST
//        	httpServletResponse.getWriter().write("Helloo");
            if(httpServletRequest.getMethod().equals("POST")){
            	String finalResponse = "";
                try {

     	           String urlParam = httpServletRequest.getRequestURI();

     	           Map<String, String[]> queryParameters =  httpServletRequest.getParameterMap();

                	//getting request body
 	        	   String body =  httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
 	        	   System.out.println("(line 71 fliter)This is request body string : "+ body);

 	        	   //getting request headers
 	        	   Enumeration<String> headerNames =  httpServletRequest.getHeaderNames();
 	        	   ArrayList<String> headerNamesList = Collections.list(headerNames);

 	        	   Map<String, String> headersKeyAndValue  = new HashMap<String, String>();

 	        	   for(String headerName : headerNamesList) {
 	        		   headersKeyAndValue.put(headerName,  httpServletRequest.getHeader(headerName));
 	        	   }

 	        	   System.out.println("header Key : "+headerNamesList.get(0));
 	        	   System.out.println("header value : "+ httpServletRequest.getHeader(headerNamesList.get(0)));

 	        	 //request body map into HashMap
 	        	   Map<String, String> requestBodyMap = new HashMap<>();

 	        	   if(body.length()!=0) {
 	        		   body = body.substring(1, body.length()-1);
 		        	   String[] bodyArr = body.split(",");

 		        	   for(String pair: bodyArr) {
 		      			 String[] entry = pair.split(":");
 		      			 
 		      			 //if body have only key doesn't have value
 		      			 if(entry.length>1) {
 		      				 requestBodyMap.put(entry[0].trim(), entry[1].trim());
 		      			 }
 		      			 else {
 		      				requestBodyMap.put(entry[0].trim(), "\"\"");
 		      			 }
 		      		 	}
 		      		 	//System.out.println(requestBodyMap.get("\"requestparams\"").substring(1,requestBodyMap.get("\"requestparams\"").length()-1));
 		      		 	System.out.println(requestBodyMap.toString());

 		      		 	finalResponse = testService.newRequestUrlInPost(requestBodyMap, headersKeyAndValue, urlParam,  queryParameters);
 		      		 	//System.out.println(testService.pathvaribleValue("http://localhost:4001"+urlParam, "http://localhost:4001/query/hesa/{id}/{name}").toString());
 		      		 System.out.println(finalResponse);
// 		      		httpServletResponse.getWriter().write("Helloo");

 	        	   }
 	        	   else {
 	        		   finalResponse = testService.newRequestUrlInPost(requestBodyMap, headersKeyAndValue, urlParam,  queryParameters);
 	        	   }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//<<<<<<< HEAD
                if(finalResponse.equals("400")) {
                	httpServletResponse.sendError(400, "There are missing parameters..!!");
                }
                else if(finalResponse.equals("500")) {
                	httpServletResponse.sendError(400, "There is no api endpoint");
                }




                else {
                	httpServletResponse.getWriter().write(finalResponse);


                }
                
            }else if

            (httpServletRequest.getMethod().equals("GET")) {
                try {
                    HashMap<String,String> headerdetails = (HashMap<String, String>) webUtils.getHeadersInfo(httpServletRequest);
                    resultmap = partitionUrlService.captureGetParameters(httpServletRequest.getRequestURI(),httpServletRequest.getParameterMap(),headerdetails);
                }catch (Exception br){
                    System.out.println(br.getMessage());



                    httpServletResponse.sendError(400,"BAD REQUEST PLEASE CHECK YOUR REQUEST PARAMETERS "+br.getMessage());
                    filterChain.doFilter(httpServletRequest,httpServletResponse);
                }
//                    removing variables with null values
//                    resultmap.remove(null);
//
//
//                httpServletResponse.getWriter().write(finalResponse);
//
//            }else if(httpServletRequest.getMet
//            hod().equals("GET")){
//                try {
//                    try{
//                        HashMap<String,String> headerdetails = (HashMap<String, String>) webUtils.getHeadersInfo(httpServletRequest);
//                        resultmap =partitionUrlService.captureGetParameters(httpServletRequest.getRequestURI(),httpServletRequest.getParameterMap(),headerdetails);
//
//
//                    }catch (Exception br){
//                        System.out.println(br.getMessage());
//                        httpServletResponse.sendError(500,"BAD REQUEST PLEASE CHECK YOUR REQUEST PARAMETERS "+br.getMessage());
//                        filterChain.doFilter(httpServletRequest,httpServletResponse);
//                    }
//
//>>>>>>> 60704f44baf7294ac68d97f790dc3c419d7ced84
                    System.out.println(resultmap);
//                    object to String mapper initialization
                    httpServletResponse.setContentType("Application/Json");
                    ObjectMapper mapper = new ObjectMapper();
                    resultmap.remove(null);
                    String json = "";
                    try {
                        json = mapper.writeValueAsString(resultmap);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    httpServletResponse.getWriter().write(json);


            }

        }
        else {
	        try{
	            filterChain.doFilter(httpServletRequest,httpServletResponse);
	        }catch (Exception ignored){

	        }
        }
    }


}