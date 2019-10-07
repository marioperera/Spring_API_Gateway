package com.test.apigateway.Controller;

import com.test.apigateway.DAO.QueryEndpoint;
import com.test.apigateway.DAO.Responsebean;
import com.test.apigateway.Services.CommonService;
import com.test.apigateway.Services.GetUrlEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
@RestController
@RequestMapping("/api")
public class Index {

    @Autowired
    GetUrlEndpointService getUrlEndpointService;

    @Autowired
    CommonService commonService;

    @GetMapping("/query/{endpoint}")
    public RequestEntity<Object> resolve(@PathVariable String endpoint, RequestEntity<Object> req_object){
       return null;

    }

    @PostMapping("/query/{endpoint}")
    public ResponseEntity<Responsebean> resolve(@PathVariable String endpoint, HashMap req_object){

        ResponseEntity responseEntity;
        System.out.println(endpoint);
        try {
            List<QueryEndpoint> endpoints= getUrlEndpointService.GetURL(endpoint);
            Responsebean resp =new Responsebean();
//            System.out.println("-- from index controller line 43 --");
//            for (QueryEndpoint queryEndpoint:endpoints
//                 ) {
//                System.out.println(queryEndpoint.getEndpoint());
//            }
            resp.setValue(commonService.get_response(req_object,endpoints));

            responseEntity =new ResponseEntity(resp,HttpStatus.OK);
            return responseEntity;

        }catch (Exception e){
            e.printStackTrace();
            Responsebean resp =new Responsebean();
            resp.setValue(e);
            responseEntity =new ResponseEntity(resp,HttpStatus.BAD_REQUEST);
            return responseEntity;
        }




    }

    @PostMapping("/register")
    public String register(RequestEntity<Object> resister_obj){
        return "ok";
    }

    @PostMapping("/test")
    public Responsebean test(){
        System.out.println("test route called --> index controller line 74 ");
        Responsebean resp =new Responsebean();
        HashMap<String,String> respmap =new HashMap<String, String>();
        respmap.put("response","hello");
        resp.setValue(respmap);
        return resp;
    }

}
