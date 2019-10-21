package com.test.apigateway.Services;

//import com.sun.jndi.toolkit.url.Uri;
import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.RegisterNewApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.nio.cs.StandardCharsets;
//import sun.nio.cs.UTF_8;

import java.net.URI;
import java.util.*;
//import java.net.URLDecoder;


@Service
public class PartitionUrlService {

    @Autowired
    RegisterApiService registerApiService;

    @Autowired
    QueryApiService queryApiService;

    public HashMap<String,String> captureGetParameters(String url, Map<String, String[]> urlparameters) throws Exception{
        HashMap<String,String> resmap =new HashMap<>();
        Map<String,String[]> requestmap =  urlparameters;
        System.out.println("came to partition url service");

        urlparameters.forEach(
                (k,v)->
                        Arrays.stream(v).forEach(
                        vv-> resmap.put(k,vv)));

        System.out.println(resmap);


        URI uri =new URI(url);

        String[] requestParameters = url.split("/");
        System.out.println(uri);
        if(requestParameters.length<=1){
            throw new Exception("invalid charachters in the url");
        }else {

            if(requestParameters[2].equals("register")){
                registerApiService.registerapi();

            }else if(requestParameters[2].equals("query")){
                queryApiService.queryapi(url,resmap);
            }
        }


        return resmap;
    }

    public HashMap<String,String > capturePostParameters(String uri) throws Exception{
//        todo
        return null;

    }
}
