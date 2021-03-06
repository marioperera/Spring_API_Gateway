package com.epic.apigateway.services;

import com.epic.apigateway.dao.QueryEndpoint;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class QueryApiService {
    @Autowired
    GetUrlEndpointService getUrlEndpointService;

    @Autowired
    CommonService commonService;

    HashMap queryapi(String url, Map<String, String> queryobjects, HashMap<String, String> headers) throws Exception {
        Logger.getAnonymousLogger().warning("Api Query");
        List<QueryEndpoint> registerNewApiObject;

//        check wither the api is registerd with the database

        try {
            registerNewApiObject =  getUrlEndpointService.GetURL(url);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new Exception("no such published api exists");
        }

        try {
            return (HashMap) commonService.get_response((HashMap<String, String>) queryobjects, registerNewApiObject, headers);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
            throw e;
        }


    }
}
