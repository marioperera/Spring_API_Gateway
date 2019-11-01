package com.epic.apigateway.services;

import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.ResponseAttribute;
import com.epic.apigateway.beans.Responsebean;
import com.epic.apigateway.utils.HeaderRequestInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */

@Service
public class CommonService {

    public HashMap get_response (HashMap req_object, List<QueryEndpoint> endpoints, HashMap<String,String> headers) throws Exception {
//        CREATE NEW REST-TEMPLATE OBJECT
//        ADD NEW HTTP INTERCEPTOR
//        SET CUSTOM HEADERS
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor(headers));
        RestTemplate restTemplate =new RestTemplate();
        restTemplate.setInterceptors(interceptors);

        HashMap request =req_object;

        request =new HashMap();

        for (QueryEndpoint endpoint:endpoints
             ) {
            String url =endpoint.getEndpoint();
//            System.out.println(url+" URI common service line 26");
            Responsebean response =new Responsebean();
//            System.out.println("endpoint type is "+endpoint.getType());

            if(endpoint.getType().equals("POST")){
                try{
                    System.out.println("post request called");
                    System.out.println(url);
                    response = restTemplate.postForObject(url,req_object,Responsebean.class);
                    ObjectMapper mapper = new ObjectMapper();
                    String json = "";
                    try {
                        json = mapper.writeValueAsString(response);
                        System.out.println("ResultingJSONstring = " + json);
                        //System.out.println(json);
                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
                        throw new Exception(url);
                    }
                }catch (Exception e){
                    throw new Exception(url);
                }

            }else {
                String url_new =this.GeneratenewGeturl(url,req_object);
                try {

                    response = restTemplate.getForObject(url_new,Responsebean.class);
                    System.out.println();
                    System.out.println("response recieved from url "+url+" "+response.getValue());

                }catch (Exception e){
                    throw new Exception(" error in "+url);
                }

            }

            LinkedHashMap<String,String> responsemap = new LinkedHashMap<String, String>();
            try{
                responsemap =(LinkedHashMap<String, String>) response.getValue();


            }catch (Exception e){
                throw new Exception(url);
            }
//          uncomment for chaining requests
//            request =new HashMap();
            try {
                if(endpoint.getResponse_attribs()!= null){
//                    System.out.println("printing response attributes for endpoint "+endpoint.getEndpoint().toString()+" "+endpoint.getResponse_attribs());
                    for (ResponseAttribute val:endpoint.getResponse_attribs()
                    ) {
//                        assert response != null;
                        String field =responsemap.get(val.getAttribute());
//                        System.out.println("required outputs from url "+url+" "+val.getAttribute()+" from responses "+responsemap.get(val.getAttribute()));
                        if(field!=null){

                            request.put(endpoint.getMappings().get(val.getAttribute()),responsemap.get(val.getAttribute()));
                            System.out.println("line 81"+request);



                        }


                    }
//                    req_object=request;

                }
//                else {
////                    uncomment for chaining requests
////                    request=responsemap;
////                    req_object=request;
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("line 101"+request);
        return request;
    }

    public Field GetAttributeValue(Object o, String FiledName) throws NullPointerException{
        Class<?> clazz = o.getClass();

        for(Field field : clazz.getDeclaredFields()) {
            //you can also use .toGenericString() instead of .getName(). This will
            //give you the type information as well.

            if(field.getName().equals(FiledName)){
                return field;
            }
        }
        return null;
    }

    private String GeneratenewGeturl(String url, HashMap<String, String> requestmap){
        StringBuilder sb =new StringBuilder(url+"?");
        requestmap.forEach((k,v)-> sb.append(k).append("=").append(v).append("&"));
        int index =sb.lastIndexOf("&");
        if(index<=0){
            sb.setLength(0);
        }else{
            sb.deleteCharAt(index);
        }

//        System.out.println("from common service");
        System.out.println(sb.toString());


        return sb.toString();
    }

}
