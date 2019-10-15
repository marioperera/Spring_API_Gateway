package com.test.apigateway.Services;

import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.ResponseAttribute;
import com.test.apigateway.Beans.Responsebean;
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

//          request will be send according to its request type

            if(endpoint.getType().equals("POST")){
                response = restTemplate.postForObject(url,req_object,Responsebean.class);
            }else {
                response = restTemplate.getForObject(url,Responsebean.class);
            }


            LinkedHashMap<String,String> responsemap =(LinkedHashMap<String, String>) response.getValue();
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

}
