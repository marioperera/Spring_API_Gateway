package com.epic.apigateway.services;

import com.epic.apigateway.mongo.documents.SavenewApiDocument;
import com.epic.apigateway.mongo.mongorepository.ApiDocumentRepository;
import com.epic.apigateway.repositories.RegisterNewApiObjectRepository;
import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.RegisterNewApiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
@Service
public class GetUrlEndpointService {

    @Autowired
    RegisterNewApiObjectRepository registerNewApiObjectRepository;

    @Autowired
    ApiDocumentRepository apiDocumentRepository;

    public List<QueryEndpoint> GetURL(String endpoint) throws Exception{
        List<Parameter> reqs =new ArrayList<Parameter>();
        System.out.println("GetURL called from line 20 ");
////        reqs.add(new Parameter("GET","hello","hello"));
//
////      Mock objects for testing
////      -------------------------------------
//        QueryEndpoint queryEndpoint =new QueryEndpoint();
//        queryEndpoint.setEndpoint("http://localhost:4001/api/test");
////        queryEndpoint.setParameters(reqs);
//        queryEndpoint.setType("POST");
//        ArrayList<ResponseAttribute> respattribs =new ArrayList();
//        respattribs.add(new ResponseAttribute("name1"));
//        queryEndpoint.setResponse_attribs(respattribs);
//        List<QueryEndpoint> endpoints=new ArrayList<QueryEndpoint>();
////      ---------------------------------------
//        QueryEndpoint queryEndpoint2 =new QueryEndpoint();
//        queryEndpoint2.setEndpoint("http://localhost:4001/api/test2");
////        queryEndpoint2.setParameters(reqs);
//        queryEndpoint2.setType("POST");
//        queryEndpoint2.setResponse_attribs(respattribs);
//
//        endpoints.add(queryEndpoint);
//        endpoints.add(queryEndpoint2);
////      ------------------------------------------
////      end of Mock objects
//
//        System.out.println("logging from GETURL service line 26 ");
//        for (QueryEndpoint ep:endpoints
//             ) {
//            System.out.println("get url service line 44 "+ep.getEndpoint());
//        }
//        return endpoints;
        System.out.println("http://localhost:4001"+endpoint);
        SavenewApiDocument endpoints = apiDocumentRepository
                                        .getDistinctByUrl("http://localhost:4001"+endpoint);

        return endpoints.getEndpoints();

    }

}
