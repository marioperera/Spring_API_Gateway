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

        for (QueryEndpoint endpoint:endpoints
             ) {
            String url =endpoint.getEndpoint();
//            System.out.println(url+" URI common service line 26");
            Responsebean response = restTemplate.postForObject(url,req_object,Responsebean.class);
//            debug code ------------------------------------------
//            System.out.println(response.getValue()+response.getCode());
//            debug code ------------------------------------------
            LinkedHashMap<String,String> responsemap =(LinkedHashMap<String, String>) response.getValue();
//            System.out.println("debug code from commonservice line 32 "+responsemap.get("response"));
            request =new HashMap();
            try {
                if(endpoint.getResponse_attribs()!= null){
                    for (ResponseAttribute val:endpoint.getResponse_attribs()
                    ) {
                        assert response != null;
                        String field =responsemap.get(val.getAttribute());
                        if(field!=null){
                            System.out.println("common service line 38 "+field);
                            request.put(val,field);

                        }
                        req_object=request;
                    }
                }
                else {
                    request=responsemap;
                    req_object=request;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
