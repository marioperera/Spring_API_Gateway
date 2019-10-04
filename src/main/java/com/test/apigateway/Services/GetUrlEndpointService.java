package com.test.apigateway.Services;

import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.RequestParameter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
@Service
public class GetUrlEndpointService {


    public List<QueryEndpoint> GetURL(String endpoint){
        List<RequestParameter> reqs =new ArrayList<RequestParameter>();
        reqs.add(new RequestParameter("GET","hello","hello"));

        QueryEndpoint queryEndpoint =new QueryEndpoint("/test",reqs);
        List<QueryEndpoint> endpoints=new ArrayList<QueryEndpoint>();
        endpoints.add(queryEndpoint);
        return endpoints;
    }

}
