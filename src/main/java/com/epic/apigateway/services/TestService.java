package com.epic.apigateway.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.RegisterNewApiObject;
import com.epic.apigateway.dao.SaveNewApiObj;
import com.epic.apigateway.repositories.RegisterNewApiObjectRepository;
import com.epic.apigateway.repositories.SaveNewApiObjRepository;

@Service
public class TestService {
	@Autowired
    RegisterNewApiObjectRepository registerNewApiObjectRepository;
	
	@Autowired
	SaveNewApiObjRepository saveNewApiObjRepo;
	
//	public String newRequestUrlInPost(Map<String, String> requestBodyMap, ArrayList<String> urlArr ) {
//		RestTemplate restTemplate = new RestTemplate();
//		
//		String url1 = "http://localhost:4001/api/hsTest/{name}";
//		String url2 = "http://localhost:4001/api/hsTest2/{some}";
//		String[] urls = {url1, url2};
//		
//		String urlResponse = "";
//		int index = 0;
//		for(String url: urls) {
//			Map<String, String> params = new HashMap<String, String>();
//			if(index==0) {
//				String value = requestBodyMap.get("\"name\"");
//				params.put("name", value);
//			}
//			else {
//				String value = requestBodyMap.get("\"some\"");
//				params.put("some", value);
//			}
//			index++;
//			urlResponse = urlResponse + " " + restTemplate.exchange(url, HttpMethod.GET, null, String.class, params).toString();	
//		}
//		return urlResponse;
//	}
	
	//get response from calling apis
	public String newRequestUrlInPost(
			Map<String, String> requestBodyMap, Map<String, String> requestHeader, String url, Map<String,String[]> queryParameters ) {
	
		String fullUrl = "http://localhost:4001"+url;
		System.out.println(fullUrl);
		
		String fullResponse = "";
		
		RegisterNewApiObject registerNewApi = new RegisterNewApiObject();
		
//		registerNewApi = registerNewApiObjectRepository.findByNewEndpoint(fullUrl);
		registerNewApi = this.slovingPathVariblesList(fullUrl);
		
		Map<String,String> pathVaribles = new HashMap<String, String>();
		
		if(registerNewApi.getNewEndpoint().indexOf("}")!=-1) {
			pathVaribles = this.pathvaribleValue(fullUrl, registerNewApi.getNewEndpoint());
		}
		else {
			pathVaribles = null;
		}
		
		List<QueryEndpoint> queryEndPoints =  registerNewApi.getQueryEndpoints();
		List<Parameter> parameters = new ArrayList<Parameter>();
		
//		RegisterNewApiObject registerNewApiTest = registerNewApiObjectRepository.findByLikeNewEndpoint(fullUrl);
//		System.out.println("this from test api : " + registerNewApiTest.getNewEndpoint());
		RestTemplate restTemplate = new RestTemplate();
		
		for(QueryEndpoint endpoint: queryEndPoints) {
			
			System.out.println(endpoint.getEndpoint());
			SaveNewApiObj saveNewApi = saveNewApiObjRepo.findByUrl(endpoint.getEndpoint());
			parameters = saveNewApi.getParameters();
			
			String endPointUrl = endpoint.getEndpoint();
			
			Map<String, String> params = new HashMap<String, String>();
			
			Map<String, String> bodyData = new HashMap<String, String>();
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);

			for(Parameter param: parameters) {
				if(!requestBodyMap.isEmpty()) {
					for(String key: requestBodyMap.keySet()) {
						if(key.substring(1,key.length()-1).equals(param.getParamname())) {
							String value = requestBodyMap.get(key);
							
							if(param.getType().equals("param")) {
								params.put(param.getParamname(), value.substring(1,value.length()-1));
								System.out.println("From url parameters : "+param.getParamname()+" : "+value.substring(1,value.length()-1));
							}
							else if(param.getType().equals("body")) {
								bodyData.put(param.getParamname(), value.substring(1,value.length()-1));
								System.out.println("From body : "+param.getParamname()+" : "+value.substring(1,value.length()-1));
							}
							else if(param.getType().equals("header")) {
								headers.set(param.getParamname(), value.substring(1,value.length()-1));
								System.out.println("From header : "+param.getParamname()+" : "+value.substring(1,value.length()-1));
							}
							else if(param.getType().equals("query")) {
								if(endPointUrl.indexOf("?")==-1) {
									endPointUrl = endPointUrl+"?"+ param.getParamname()+ "="+ value.substring(1,value.length()-1);
								}
								else {
									endPointUrl = endPointUrl+"&"+param.getParamname()+ "="+ value.substring(1,value.length()-1);
								}
								System.out.println("From body query : "+param.getParamname()+" : "+value);
							}
						}
					}
				}
				if(!requestHeader.isEmpty()) {
					for(String key: requestHeader.keySet()) {
						if(key.equals(param.getParamname())) {
							String value = requestHeader.get(key);
							
							if(param.getType().equals("param")) {
								params.put(param.getParamname(), value);
								System.out.println("From header url parameters : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("body")) {
								bodyData.put(param.getParamname(), value);
								System.out.println("From header body : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("header")) {
								headers.set(param.getParamname(), value);
								System.out.println("From header header : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("query")) {
								if(endPointUrl.indexOf("?")==-1) {
									endPointUrl = endPointUrl+"?"+ param.getParamname()+ "="+ value;
								}
								else {
									endPointUrl = endPointUrl+"&"+param.getParamname()+ "="+ value;
								}
								System.out.println("From header query : "+param.getParamname()+" : "+value);
							}
						}
					}
				}
				if(!queryParameters.isEmpty()) {
					for(String key: queryParameters.keySet()) {
						if(key.equals(param.getParamname())) {
							String value = queryParameters.get(key)[0];
							
							if(param.getType().equals("param")) {
								params.put(param.getParamname(), value);
								System.out.println("From header url parameters : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("body")) {
								bodyData.put(param.getParamname(), value);
								System.out.println("From header body : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("header")) {
								headers.set(param.getParamname(), value);
								System.out.println("From header header : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("query")) {
								if(endPointUrl.indexOf("?")==-1) {
									endPointUrl = endPointUrl+"?"+ param.getParamname()+ "="+ value;
								}
								else {
									endPointUrl = endPointUrl+"&"+param.getParamname()+ "="+ value;
								}
								System.out.println("From header query : "+param.getParamname()+" : "+value);
							}
						}
					}
				}
				if(pathVaribles != null) {
					for(String key: pathVaribles.keySet()) {
						if(key.equals(param.getParamname())) {
							String value = pathVaribles.get(key);
							
							if(param.getType().equals("param")) {
								params.put(param.getParamname(), value);
								System.out.println("From header url parameters : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("body")) {
								bodyData.put(param.getParamname(), value);
								System.out.println("From header body : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("header")) {
								headers.set(param.getParamname(), value);
								System.out.println("From header header : "+param.getParamname()+" : "+value);
							}
							else if(param.getType().equals("query")) {
								if(endPointUrl.indexOf("?")==-1) {
									endPointUrl = endPointUrl+"?"+ param.getParamname()+ "="+ value;
								}
								else {
									endPointUrl = endPointUrl+"&"+param.getParamname()+ "="+ value;
								}
								System.out.println("From header query : "+param.getParamname()+" : "+value);
							}
						}
					}
				}
			}
			String typeOfEndpoint = saveNewApi.getType();
			if(typeOfEndpoint.equals("GET")) {
				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				
				try {
					String response = restTemplate.exchange(endPointUrl, HttpMethod.GET, entity, String.class, params).getBody();
					fullResponse = fullResponse + " " +response+"\n";
				}catch(Exception e) {
					System.out.println("error : "+e);
				}
			}
			else if(typeOfEndpoint.equals("POST")) {
				HttpEntity<Object> entity = new HttpEntity<Object>(bodyData, headers);
				
				try {
					String response = restTemplate.exchange(endPointUrl, HttpMethod.POST, entity, String.class, params).getBody();
					fullResponse = fullResponse + response +"\n";
				}catch(Exception e) {
					System.out.println("error : "+e);
				}
			}
			
		}
		return fullResponse;
	}
	
	//Creating new api with path parameters
//	public RegisterNewApiObject slovingPathVaribles(String url) {
//		
//		Map<Integer, String> pathVaribleValues = new HashMap<Integer, String>();
//		
//		RegisterNewApiObject registerNewApiTest = null;
//		
//		registerNewApiTest = registerNewApiObjectRepository.findByLikeNewEndpoint(url);
//		
//		String pathVarible = "";
//		int index = 1;
//		
//		while(registerNewApiTest==null) {
//			
//			int lastIndexofSlash = url.lastIndexOf("/");
//			
//			pathVarible = url.substring(lastIndexofSlash+1);
//			System.out.println(pathVarible);
//			
//			pathVaribleValues.put(index, pathVarible);
//			
//			url = url.substring(0,lastIndexofSlash);
//					
//			System.out.println("This is form url : "+url);
//			registerNewApiTest = registerNewApiObjectRepository.findByLikeNewEndpoint(url);
//		}
//		
//		System.out.println("This is url from object : "+registerNewApiTest.getNewEndpoint());
//
//		return registerNewApiTest;
//		
//	}
	
public RegisterNewApiObject slovingPathVariblesList(String url) {
		
		String originalUrl = url;
		Map<Integer, String> pathVaribleValues = new HashMap<Integer, String>();
		
		List<RegisterNewApiObject> registerNewApiTestList = null;
		RegisterNewApiObject registerNewApi = null;
		
		registerNewApiTestList = registerNewApiObjectRepository.findByLikeNewEndpointList(url);
		
		String pathVarible = "";
		int index = 1;
		
		while(registerNewApiTestList.isEmpty()) {
			
			int lastIndexofSlash = url.lastIndexOf("/");
			
			pathVarible = url.substring(lastIndexofSlash+1);
			System.out.println(pathVarible);
			
			pathVaribleValues.put(index, pathVarible);
			
			url = url.substring(0,lastIndexofSlash);
					
			System.out.println("This is form url : "+url);
			registerNewApiTestList = registerNewApiObjectRepository.findByLikeNewEndpointList(url);
		}
		
		String[] urlArr = originalUrl.split("/");
		for(RegisterNewApiObject newApi : registerNewApiTestList) {
			
			String[] savedUrlArr = newApi.getNewEndpoint().split("/");
			if(urlArr.length == savedUrlArr.length) {
				registerNewApi = newApi;
				break;
			}
		}
	
		System.out.println("This is url from object : "+registerNewApi.getNewEndpoint());

		return registerNewApi;
		
	}
	
	public Map<String, String> pathvaribleValue(String callingUrl, String savedUrl){
		
		int index = 0;
		
		Map<String, String> pathVaribles = new HashMap<String, String>();
		while(index!=-1) {
			
			int lastIndexofSlashCallingUrl = callingUrl.lastIndexOf("/");
			int lastIndexofSlashSavedUrl =  savedUrl.lastIndexOf("/");
			
			String pathVaribleName = savedUrl.substring(savedUrl.lastIndexOf("{")+1, savedUrl.lastIndexOf("}"));
			String pathVaribleValue = callingUrl.substring(callingUrl.lastIndexOf("/")+1);
			pathVaribles.put(pathVaribleName, pathVaribleValue);
			
			callingUrl = callingUrl.substring(0, lastIndexofSlashCallingUrl);
			savedUrl = savedUrl.substring(0, lastIndexofSlashSavedUrl);
			
			index = savedUrl.indexOf("{");
		}
		System.out.println("From path varible map : "+ pathVaribles);
		return pathVaribles;
	}
	
	public void testingFunction(String url) {
	}
	
	
}
