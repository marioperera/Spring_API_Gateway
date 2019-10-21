package com.test.apigateway.Services;

import com.test.apigateway.DAO.QueryEndpoint;
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

    public void queryapi(String url, Map queryobjects) throws Exception {
        Logger.getAnonymousLogger().warning("Api Query");
        List<QueryEndpoint> registerNewApiObject;

        try {
            registerNewApiObject = (List<QueryEndpoint>) getUrlEndpointService.GetURL(url);
        }catch (HibernateException e){
            e.printStackTrace();
            throw new Exception("no such published api exists");
        }
        System.out.println(registerNewApiObject.size());
        commonService.get_response((HashMap) queryobjects,registerNewApiObject);
        return;

    }
}
