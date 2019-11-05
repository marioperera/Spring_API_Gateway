package com.epic.apigateway.services;

import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.ResponseAttribute;
import com.epic.apigateway.beans.Responsebean;
import com.epic.apigateway.dao.SaveNewApiObj;
import com.epic.apigateway.utils.HeaderRequestInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author mario_p
 * @Date 10/4/2019
 */

@Service
public class CommonService {

    public HashMap get_response (HashMap<String,String> req_object, List<QueryEndpoint> endpoints, HashMap<String,String> headers) throws Exception {
//        CREATE NEW REST-TEMPLATE OBJECT
//        ADD NEW HTTP INTERCEPTOR
//        SET CUSTOM HEADERS

        RestTemplate restTemplate =new RestTemplate();

        HashSet<String> REQUEST_PARAMETER_LIST = new HashSet<>();

        for (QueryEndpoint endpoint:endpoints
             ) {
            endpoint.getParameters().forEach(P ->{
                REQUEST_PARAMETER_LIST.add(P.getParamname());
            });
        }
        System.out.println(" request parameter list "+REQUEST_PARAMETER_LIST);
        System.out.println(" key set "+req_object.keySet());
        Map<String,Object> request = new HashMap<String,Object>();
        request =new HashMap();
        for (String request_param:REQUEST_PARAMETER_LIST
             ) {
            if(!req_object.keySet().contains(request_param)){
                throw new Exception("DOES NOT CONTAIN PARAMETER ID "+request_param);
            }else{
                for (QueryEndpoint endpoint:endpoints
                ) {
                    String url =endpoint.getEndpoint();

                    Responsebean response =new Responsebean();

                    String RECIEVED_RESPONSE ="";
                    try {
                        RECIEVED_RESPONSE =make_rest_request(endpoint,req_object,restTemplate);
                        System.out.println("recieved response form "+url+" \n"+RECIEVED_RESPONSE);
                    } catch (Exception e) {
                        throw e;
                    }


                    HashMap<String,String> responsemap = new HashMap<>();
                    try{
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String,Object> map =mapper.readValue(RECIEVED_RESPONSE,Map.class);
                        response.setValue(map);


                        map.forEach((k,v)->{
                            String vv =String.valueOf(v);
                            responsemap.put(k,vv);
                        });
                        System.out.println(responsemap+" common service line 77");


                    }catch (Exception e){
                        throw new Exception(url);
                    }

                    try {
                        if(endpoint.getResponse_attribs()!= null){
//                    System.out.println("printing response attributes for endpoint "+endpoint.getEndpoint().toString()+" "+endpoint.getResponse_attribs());
                            for (ResponseAttribute val:endpoint.getResponse_attribs()
                            ) {
//                        assert response != null;
                                String field = (String) responsemap.get( ""+val.getAttribute());
                        System.out.println("required outputs from url "+url+" "+val.getAttribute()+" from responses "+responsemap.get(val.getAttribute()));
                                if(field!=null){

                                    request.put(endpoint.getMappings().get(val.getAttribute()),responsemap.get(""+val.getAttribute()));
                                    System.out.println("line 81"+request);

                                }

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("line 101"+request);

            }
        }
        return (HashMap) request;


    }

    private String make_rest_request(QueryEndpoint endpoint,HashMap<String,String> req_object,RestTemplate restTemplate) throws Exception {
        String endPointUrl =endpoint.getEndpoint();

        String Type =endpoint.getType();

        List<Parameter> REQUEST_PARAMETERS =new ArrayList<>();

        REQUEST_PARAMETERS =endpoint.getParameters();

        Map<String, String> params = new HashMap<String, String>();

        Map<String, String> bodyData = new HashMap<String, String>();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        String response = "";

        for (Parameter param:REQUEST_PARAMETERS
             ) {
            if(param.getType().equals("param")){
                params.put(param.getParamname(),req_object.get(param.getParamname()));
            }else if(param.getType().equals("body") && !endpoint.getType().equals("GET")){
                bodyData.put(param.getParamname(),req_object.get(param.getParamname()));
            }else if(param.getType().equals("header")){
                headers.set(param.getParamname(),req_object.get(param.getParamname()));
            }else if(param.getType().equals("query")){
                if(!endPointUrl.contains("?")) {
                    endPointUrl = endPointUrl+"?"+ param.getParamname()+ "="+ req_object.get(param.getParamname());
                }
                else {
                    endPointUrl = endPointUrl+"&"+param.getParamname()+ "="+ req_object.get(param.getParamname());
                }
                System.out.println("From header query : "+param.getParamname()+" : "+req_object.get(param.getParamname()));
            }
        }
        try {
            if (Type.equals("POST")){
                HttpEntity<Object> entity = new HttpEntity<Object>(bodyData, headers);

                response = restTemplate.exchange(endPointUrl, HttpMethod.POST, entity, String.class, params).getBody();
                System.out.println(response +" line 156 common service");
            }else if (Type.equals("GET")){
                HttpEntity<Object> entity = new HttpEntity<Object>(headers);

                response = restTemplate.exchange(endPointUrl, HttpMethod.GET, entity, String.class, params).getBody();
                System.out.println(response +" line 161 common service");
            }
        } catch (RestClientException e) {
            throw new Exception("problem reaching url "+endPointUrl);
        }

        return response;
    }

}
