package com.epic.apigateway.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epic.apigateway.dao.Mapping;
import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.RegisterNewApiObject;
import com.epic.apigateway.dao.SaveNewApiObj;
import com.epic.apigateway.mongo.documents.SavenewApiDocument;
import com.epic.apigateway.mongo.mongorepository.ApiDocumentRepository;
import com.epic.apigateway.repositories.RegisterNewApiObjectRepository;
import com.epic.apigateway.repositories.SaveNewApiObjRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class TestService {
	@Autowired
    RegisterNewApiObjectRepository registerNewApiObjectRepository;
	
	@Autowired
	SaveNewApiObjRepository saveNewApiObjRepo;
	
	@Autowired
	ApiDocumentRepository apiDocumentRepository;
	
	//get response from calling apis, Querying apis
	public String newRequestUrlInPost(
			Map<String, String> requestBodyMap, Map<String, String> requestHeader, String url, Map<String,String[]> queryParameters ) {
	
		String fullUrl = "http://localhost:4001"+url;
		System.out.println(fullUrl);
		
		String fullResponse = "";
		HashMap<String, String> fullMapResponse = new HashMap<String, String>();
		
		SavenewApiDocument registerNewApi = new SavenewApiDocument();
		
		registerNewApi = this.slovingPathVariblesList(fullUrl);
		if(registerNewApi != null) {
		
			Map<String,String> pathVaribles = new HashMap<String, String>();
			
			if(registerNewApi.getUrl().indexOf("}")!=-1) {
				
				pathVaribles = this.pathvaribleValue(fullUrl, registerNewApi.getUrl());
			}

			List<QueryEndpoint> queryEndPoints =  registerNewApi.getEndpoints();
			List<Parameter> parameters = new ArrayList<Parameter>();
			
			//After this should have a validation
			Boolean validation = this.validationRequestParameters(queryEndPoints, requestBodyMap, requestHeader, queryParameters, pathVaribles);
			
			if(validation) {
				RestTemplate restTemplate = new RestTemplate();
				int queryEndpointCount = 1;
				
				for(QueryEndpoint endpoint: queryEndPoints) {
					
					System.out.println(endpoint.getEndpoint());
					//System.out.println("This is Parameter list : "+ endpoint.getParameters().get(0).getParamname());
					
					//get api object
//					SaveNewApiObj saveNewApi = saveNewApiObjRepo.findByUrlAndType(endpoint.getEndpoint(), endpoint.getType());
					parameters = endpoint.getParameters();
					
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
						
						if(!pathVaribles.isEmpty() || pathVaribles != null) {
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
					
					String typeOfEndpoint = endpoint.getType();
					if(typeOfEndpoint.equals("GET")) {
						HttpEntity<Object> entity = new HttpEntity<Object>(headers);
						
						try {
							String response = restTemplate.exchange(endPointUrl, HttpMethod.GET, entity, String.class, params).getBody();
							
							//String mappedResponse = this.mappingResponseObject(response, endpoint);
							HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
							fullMapResponse.putAll(mapResponse);
							
							fullResponse = fullResponse + " " +response+"\n";
						}catch(Exception e) {
							System.out.println("error : "+e);
							try {
								String response = restTemplate.exchange(endPointUrl, HttpMethod.GET, entity, String.class, params).getBody();
								
								HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
								fullMapResponse.putAll(mapResponse);
								
								fullResponse = fullResponse + " " +response+"\n";
							}catch(Exception ex){
								System.out.println("error : "+ex);
								fullResponse = "500";
							}
						}
					}
					else if(typeOfEndpoint.equals("DELETE")) {
						HttpEntity<Object> entity = new HttpEntity<Object>(headers);
						
						try {
							String response = restTemplate.exchange(endPointUrl, HttpMethod.DELETE, entity, String.class, params).getBody();
							
							HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
							fullMapResponse.putAll(mapResponse);
							
							fullResponse = fullResponse + " " +response+"\n";
						}catch(Exception e) {
							System.out.println("error : "+e);
							fullResponse = "500";
						}
					}
					else if(typeOfEndpoint.equals("POST")) {
						HttpEntity<Object> entity = new HttpEntity<Object>(bodyData, headers);
						
						try {
							String response = restTemplate.exchange(endPointUrl, HttpMethod.POST, entity, String.class, params).getBody();
							
							HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
							fullMapResponse.putAll(mapResponse);
							
							fullResponse = fullResponse + response +"\n";
						}catch(Exception e) {
							System.out.println("error : "+e);
							try {
								String response = restTemplate.exchange(endPointUrl, HttpMethod.POST, entity, String.class, params).getBody();
								
								HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
								fullMapResponse.putAll(mapResponse);
								
								fullResponse = fullResponse + " " +response+"\n";
							}catch(Exception ex){
								System.out.println("error : "+ex);
								fullResponse = "500";
							}
						}
					}
					else if(typeOfEndpoint.equals("PUT")) {
						HttpEntity<Object> entity = new HttpEntity<Object>(bodyData, headers);
						
						try {
							String response = restTemplate.exchange(endPointUrl, HttpMethod.PUT, entity, String.class, params).getBody();
							
							HashMap<String, String> mapResponse = this.mappingResponseObject(response, endpoint, queryEndpointCount);
							fullMapResponse.putAll(mapResponse);
							
							fullResponse = fullResponse + response + "\n";
						}catch(Exception e) {
							System.out.println("error : "+ e);
							fullResponse = "500";
						}
					}
				queryEndpointCount++;	
				}
				
				//Convert response object to json string
				if(!fullResponse.equals("500")) {
					
					ObjectMapper mapper = new ObjectMapper();
					fullMapResponse.remove(null);
			        String json = "";
			        
			        try {
			            json = mapper.writeValueAsString(fullMapResponse);

			        } catch (JsonProcessingException e) {
			            e.printStackTrace();
			        }
			        
			        return json;
				}
				else {
					return fullResponse;
				}
			}
			else {
				return "400";
			}
			
		}
		else {
			return "404";
		}
	}
	
public SavenewApiDocument slovingPathVariblesList(String url) {
		
		String originalUrl = url;
		Map<Integer, String> pathVaribleValues = new HashMap<Integer, String>();
		
		List<SavenewApiDocument> registerNewApiTestList = null;
		SavenewApiDocument registerNewApi = null;
		
		registerNewApiTestList = apiDocumentRepository.findByUrlLike(url);
		
		String pathVarible = "";
		int index = 1;
		
		//slove same length url call as same
		int bracketCount=0;
		
		while(registerNewApiTestList.isEmpty() && url.length()>27) {
			
			int lastIndexofSlash = url.lastIndexOf("/");
			
			pathVarible = url.substring(lastIndexofSlash+1);
			System.out.println(pathVarible);
			
			pathVaribleValues.put(index, pathVarible);
			
			url = url.substring(0,lastIndexofSlash);
			
			bracketCount++;
			
			System.out.println("This is form url : "+url);
			registerNewApiTestList = apiDocumentRepository.findByUrlLike(url);
		}
		
		
		String[] urlArr = originalUrl.split("/");
		int savedBracketcount = 0;
		
		if(registerNewApiTestList.size()>0) {
			for(SavenewApiDocument newApi : registerNewApiTestList) {
				
				String[] savedUrlArr = newApi.getUrl().split("/");
				if(urlArr.length == savedUrlArr.length) {
					for(String urlparts: savedUrlArr) {
						
						//same length parameter error slove
						if(urlparts.contains("{")) {
							savedBracketcount++;
						}
					}
					if(savedBracketcount==bracketCount) {
						registerNewApi = newApi;
						break;
					}
				}
			}
		}

		return registerNewApi;
	}
	
//Getting path variable values
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
	
	//validating request parameters
	public Boolean validationRequestParameters(List<QueryEndpoint> queryEndpoints, Map<String, String> requestBodyMap,
			Map<String, String> requestHeader, Map<String,String[]> queryParameters, Map<String, String> pathVaribles) {
		
		//Request parameters in database
		List<String> endpointsMandParameters = new ArrayList<String>();
		
		queryEndpoints.forEach(endpoint -> {
//			SaveNewApiObj saveNewApi = saveNewApiObjRepo.findByUrlAndType(endpoint.getEndpoint(), endpoint.getType());
			List<Parameter> params = endpoint.getParameters();
			
			//collect mandatory parameters
			params.stream().forEach(param->{
				if(param.getMandatory()&& !endpointsMandParameters.contains(param.getParamname())) {
					endpointsMandParameters.add(param.getParamname());
				}});
		});
		
		//Request parameters from filter
		List<String> parameterFilter = new ArrayList<String>(); 
		
		//RequestBody parameters
		if(!requestBodyMap.isEmpty()) {
			requestBodyMap.entrySet().stream()
			.forEach(entry -> parameterFilter.add(entry.getKey().substring(1, entry.getKey().length()-1)));
		}
		
		//Header Parameters
		if(!requestHeader.isEmpty()) {
			requestHeader.entrySet().stream()
			.forEach(entry -> parameterFilter.add(entry.getKey()));
		}
		
		//Query parameters
		if(!queryParameters.isEmpty()) {
			queryParameters.entrySet().stream()
			.forEach(entry -> parameterFilter.add(entry.getKey()));
		}
		
		//Path variables
		if(!pathVaribles.isEmpty()) {
			pathVaribles.entrySet().stream()
			.forEach(entry -> parameterFilter.add(entry.getKey()));
		}	
		
		//Checking mand parameters have in parameterFilter list
		long numOfMandParam = parameterFilter.stream().filter(parameterName -> endpointsMandParameters.contains(parameterName)).count();
		
		System.out.println("Test Service line 412 : " + numOfMandParam);
		
		if(numOfMandParam >= endpointsMandParameters.size()) {
			if(this.parameterValueEmptyOrNot(endpointsMandParameters, requestBodyMap, requestHeader, queryParameters, pathVaribles)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
	}
	
	//Checking mandatory parameters have empty value or not
	public Boolean parameterValueEmptyOrNot(List<String> mandtoryParameters, Map<String, String> requestBodyMap,
			Map<String, String> requestHeader, Map<String,String[]> queryParameters, Map<String, String> pathVaribles) {
	
		int parameterValueCount = 0;
		
		for(String mandtroyParamaterName : mandtoryParameters) {
			
			//Checking request Body
			if(requestBodyMap.containsKey("\""+ mandtroyParamaterName+"\"")){
				if(!requestBodyMap.get("\""+ mandtroyParamaterName+"\"").equals("\"\"")) {
					parameterValueCount++;
				}
			}
			
			//Checking Request Header
			else if(requestHeader.containsKey(mandtroyParamaterName)) {
				if(!requestHeader.get(mandtroyParamaterName).equals("")) {
					parameterValueCount++;
				}
			}
			
			//Checking Query Parameters
			else if(queryParameters.containsKey(mandtroyParamaterName)) {
				if(!queryParameters.get(mandtroyParamaterName)[0].equals("")) {
					parameterValueCount++;
				}
			}
			
			//Checking Path Variables
			else if(pathVaribles.containsKey(mandtroyParamaterName)) {
				if(!pathVaribles.get(mandtroyParamaterName).equals("")) {
					parameterValueCount++;
				}
			}
		}
		
		//given parameters and mandatory parameters
		if(mandtoryParameters.size()==parameterValueCount) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public HashMap<String, String> mappingResponseObject(String responseString, QueryEndpoint endpoint, int count) {
		
		HashMap<String, String> response_map = new HashMap<>();
		HashMap<String, String> mappedResponse = new HashMap<String, String>();
		HashMap<String, String> mappings = endpoint.getMappings();

		ObjectMapper mapper = new ObjectMapper();
		
		if(responseString.charAt(0)!='[') {
			try {
	            Map<String, Object> map = mapper.readValue(responseString, Map.class);
	            System.out.println(map.toString());
	            map.forEach((k, v) -> {
	            	
	                String vv = String.valueOf(v);
	                response_map.put(k, vv);
	                
	            });	
	           
			} catch (Exception e) {
				    System.out.println(e);
			}
	       	        
	        for(String key: response_map.keySet()) {
	        	
	        	if(mappings.containsKey(key)) {
	        		mappedResponse.put(mappings.get(key), response_map.get(key));
	        	}
	        }
	        
	        mappedResponse.remove(null);
	        String json = "";
	        try {
	            json = mapper.writeValueAsString(mappedResponse);

	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		}
		else {

			mappedResponse = this.mappingArrayResponse(responseString, mappings, count);
		}
              
        
		return mappedResponse;
	}
	
	public HashMap<String, String> mappingArrayResponse(String responseArrString, HashMap<String, String> mappings, int count){

		String responseStringWithoutLineSpaces = responseArrString.replaceAll("[\\n\\t ]", "");

		
//		String stringResponseArr[] = responseStringWithoutLineSpaces.split("\\{");
		
		ArrayList<String> stringResponseArrArrayList = this.getStringArr(responseStringWithoutLineSpaces);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList<HashMap<String, String>> mappedResponseArr = new ArrayList<HashMap<String,String>>();
		
		HashMap<String, String> returnResponse = new HashMap<String, String>();
		
		for(int i=1; i<stringResponseArrArrayList.size(); i++) {
			
			String response = stringResponseArrArrayList.get(i);
			
			HashMap<String, String> response_map = new HashMap<String, String>();
			
			HashMap<String, String> mappedResponse = new HashMap<String, String>();
			
			
			try {
	            Map<String, Object> map = mapper.readValue(response, Map.class);
	            
	            map.forEach((k, v) -> {
	            	
	                String vv = String.valueOf(v);
	                response_map.put(k, vv);
	                
	            });	
	           
			} catch (Exception e) {
				    System.out.println(e);
			}
			
			for(String key: response_map.keySet()) {
	        	if(mappings.containsKey(key)) {
	        		mappedResponse.put(mappings.get(key), response_map.get(key));
	        	}
	        }
			
			mappedResponseArr.add(mappedResponse);
			//System.out.println(mappedResponse.toString());
		}
		System.out.println(mappedResponseArr);
		returnResponse.put(Integer.toString(count), mappedResponseArr.toString());
		
		
		return returnResponse;
	}
	
	public ArrayList<String> getStringArr(String responseString) {
		
		ArrayList<String> responseArr = new ArrayList<String>();
		
		Stack<String> stack = new Stack<String>();
		
		int fristIndex = 0;
		int lastIndex = 0;
		
		for(int i=0; i<responseString.length(); i++) {
			
			if(responseString.charAt(i)=='{') {
				if(stack.isEmpty()) {
					fristIndex = i;
				}
				stack.push("{");
			}
			else if(responseString.charAt(i)=='}'){
				if(stack.size()==1) {
					lastIndex = i+1;
				}
				stack.pop();
			}
			
			if( i!=0 && stack.isEmpty()) {
				responseArr.add(responseString.substring(fristIndex, lastIndex));
			}
		}
			
		return responseArr;
	}
	
}
