package com.test.apigateway.Services;

import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.Responsebean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
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
            Responsebean response = restTemplate.postForObject(url,req_object,Responsebean.class);
            request =new HashMap();
            for (String val:endpoint.getResponse_attribs()
                 ) {
                assert response != null;
                Field field =GetAttributeValue(response.getValue(),val);
                if(field!=null){
                    request.put(val,field.toGenericString());
                }
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
