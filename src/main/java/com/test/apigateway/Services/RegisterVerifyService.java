package com.test.apigateway.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.test.apigateway.DAO.Parameter;

@Service
public class RegisterVerifyService {

	//verify api
	public int validateApi(String endpoint, String apiType, List<Parameter> requestParameters, List<String> requestValues) {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
		final  String url = endpoint;
		RestTemplate restTemplate = new RestTemplate();
		
		// validate get and delete api's
		if(apiType.equals("GET")|| apiType.equals("DELETE")) {
			
			if(requestParameters==null || requestParameters.isEmpty()) {
				try {
					return restTemplate.getForEntity(url, String.class).getStatusCodeValue();
				}
				catch(HttpClientErrorException e){
					return 400;
				}
			}
			else {
		
				int index = 0;
				Map<String, String> params = new HashMap<String, String>();
				
				for(Parameter request: requestParameters) {

					if(request.getType().equals("param")) {
						params.put(request.getParamname(), requestValues.get(index));
					}
					
					if(request.getType().equals("header")) {
						headers.set(request.getParamname(), requestValues.get(index));
					}
					index++;
				}
				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				try {
					if(apiType.equals("GET")) {
//						return restTemplate.exchange(url, HttpMethod.GET, entity, String.class, params).getStatusCodeValue();
						return 200;
					}
					else {
						return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class, params).getStatusCodeValue();
					}
				}
				catch(HttpClientErrorException e) {
					return 400;
				}
				
			}
		}
		
		//validate post and put api's
		else if(apiType.equals("POST") || apiType.equals("PUT")) {
			if(requestParameters==null || requestParameters.isEmpty()) {
				try {
					return restTemplate.postForEntity(url, null, String.class).getStatusCodeValue();
				}
				catch(HttpClientErrorException e){
					return 400;
				}
			}
			else {
				
				HashMap<String, String> bodyData = new HashMap<String, String>();
				Map<String, String> params = new HashMap<String, String>();
				
				int index = 0;
				for(Parameter request: requestParameters) {
					
					if(request.getType().equals("body")) {
						bodyData.put(request.getParamname(), requestValues.get(index));
					}
					else if(request.getType().equals("header")) {
						headers.set(request.getParamname(), requestValues.get(index));
					}
					else if(request.getType().equals("param")) {
						params.put(request.getParamname(), requestValues.get(index));
					}
					
					index++;
				}
				HttpEntity<Object> entity = new HttpEntity<Object>(bodyData,headers);
				
				try {
					if(apiType.equals("POST")) {
						return restTemplate.postForEntity(url, entity, String.class, params).getStatusCodeValue();
					}
					else {
						return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, params).getStatusCodeValue();
					}
				}
				catch (HttpClientErrorException e) {
					return 400;
				}
			}
		}
		else {
			return 400;
		}
	}
}
