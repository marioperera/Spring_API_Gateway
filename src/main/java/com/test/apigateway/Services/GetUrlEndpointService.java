package com.test.apigateway.Services;

import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.Parameter;
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
        List<Parameter> reqs =new ArrayList<Parameter>();
        System.out.println("GetURL called from line 20 ");
        reqs.add(new Parameter("GET","hello","hello"));

        QueryEndpoint queryEndpoint =new QueryEndpoint();
        queryEndpoint.setEndpoint("http://localhost:4001/api/test");
        queryEndpoint.setParameters(reqs);
        queryEndpoint.setResponse_attribs(new String[]{"response"});
        List<QueryEndpoint> endpoints=new ArrayList<QueryEndpoint>();
        endpoints.add(queryEndpoint);
        System.out.println("logging from GETURL service line 26 ");
        for (QueryEndpoint ep:endpoints
             ) {
            System.out.println(ep.getEndpoint());
        }
        return endpoints;
    }

}
