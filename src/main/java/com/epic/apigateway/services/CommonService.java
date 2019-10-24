package com.epic.apigateway.services;

import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.ResponseAttribute;
import com.epic.apigateway.beans.Responsebean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */

@Service
public class CommonService {

    public HashMap get_response (HashMap req_object, List<QueryEndpoint> endpoints) throws NullPointerException{
        RestTemplate restTemplate =new RestTemplate();
        HashMap request =req_object;

        request =new HashMap();

        for (QueryEndpoint endpoint:endpoints
             ) {
            String url =endpoint.getEndpoint();
//            System.out.println(url+" URI common service line 26");
            Responsebean response =new Responsebean();
//            System.out.println("endpoint type is "+endpoint.getType());

//            this.GeneratenewGeturl(url,req_object);
//          request will be sent according to its request type

            if(endpoint.getType().equals("POST")){
                try{
                    System.out.println("post request called");
                    System.out.println(url);
                    response = restTemplate.postForObject(url,req_object,Responsebean.class);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                String url_new =this.GeneratenewGeturl(url,req_object);
                try {

                    response = restTemplate.getForObject(url_new,Responsebean.class);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            LinkedHashMap<String,String> responsemap = new LinkedHashMap<String, String>();
            try{
                responsemap =(LinkedHashMap<String, String>) response.getValue();

            }catch (Exception e){
                e.printStackTrace();
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
                        System.out.println("common service line 44 attributes "+val.getAttribute());
                        if(field!=null){

                            request.put(val.getAttribute(),field);
                            System.out.println("checking added keys to the key set"+request.keySet());


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
        System.out.println(request);
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

    public String GeneratenewGeturl(String url,HashMap<String,String> requestmap){
        StringBuilder sb =new StringBuilder(url+"?");
        requestmap.forEach((k,v)->sb.append(k+"="+v+"&"));
        int index =sb.lastIndexOf("&");
        sb.deleteCharAt(index);
        System.out.println("from common service");
        System.out.println(sb.toString());


        return sb.toString();
    }

}
