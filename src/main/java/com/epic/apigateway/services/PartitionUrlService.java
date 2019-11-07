package com.epic.apigateway.services;

import com.epic.apigateway.dao.QueryEndpoint;
import org.hibernate.HibernateException;
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
    CommonService commonService;

    @Autowired
    RegisterApiService registerApiService;

    @Autowired
    QueryApiService queryApiService;

    @Autowired
    GetUrlEndpointService getUrlEndpointService;

    public HashMap<String,String> captureGetParameters(String url, Map<String, String[]> urlparameters, HashMap<String,String> headers) throws Exception{
        HashMap<String,String> resmap =new HashMap<>();
        Map<String,String[]> requestmap =  urlparameters;
        System.out.println("came to partition url service");

//        map the original URL query parameters to a Hashmap

        urlparameters.forEach(
                (k,v)->
                        Arrays.stream(v).forEach(
                        vv-> resmap.put(k,vv)));

        System.out.println(resmap);


        URI uri =new URI(url);

//        break the current url by the  "/" for finding path variables

        String[] requestParameters = url.split("/");
        System.out.println(uri);
        if(requestParameters.length<=1){
            throw new Exception("invalid charachters in the url");
        }else {

            if(requestParameters[2].equals("register")){
//                registerApiService.registerapi();

            }else if(requestParameters[2].equals("query")){
                try {
                    return queryApiService.queryapi(url,resmap,headers);
                } catch (Exception e) {
                    throw e;
                }
            }

        }


        return resmap;
    }

    public HashMap<String,String > capturePostParameters(String uri) throws Exception{

        List<QueryEndpoint> registerNewApiObject;

        try {
            registerNewApiObject = (List<QueryEndpoint>) getUrlEndpointService.GetURL(uri);
        }catch (HibernateException e){
            e.printStackTrace();
            throw new Exception("no such published api exists");
        }
        System.out.println(registerNewApiObject.size());
//        return commonService.get_response((HashMap) queryobjects,registerNewApiObject);

        return null;

    }
}
