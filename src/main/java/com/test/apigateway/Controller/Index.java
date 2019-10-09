package com.test.apigateway.Controller;

import com.test.apigateway.Beans.RegisterApiTemplateBean;
import com.test.apigateway.Beans.SaveNewApiBean;
import com.test.apigateway.DAO.*;
import com.test.apigateway.Beans.Responsebean;
import com.test.apigateway.Repositories.SaveNewApiObjRepository;
import com.test.apigateway.Services.CommonService;
import com.test.apigateway.Services.GenrateListService;
import com.test.apigateway.Services.GetUrlEndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author mario_p
 * @Date 10/4/2019
 */
@RestController
@RequestMapping("/api")
public class Index {
    @Autowired
    SaveNewApiObjRepository saveNewApiObjRepository;

    @Autowired
    GenrateListService genrateListService;

    @Autowired
    GetUrlEndpointService getUrlEndpointService;

    @Autowired
    CommonService commonService;

    @GetMapping("/query/{endpoint}")
    public RequestEntity<Object> resolve(@PathVariable String endpoint, RequestEntity<Object> req_object){
       return null;

    }

    @PostMapping("/query/{endpoint}")
    public ResponseEntity<Responsebean> resolve(@RequestBody HashMap<String,String> req_object,@PathVariable String endpoint){

        System.out.println("index controller line 39 "+req_object.keySet());
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

//    /addApi request model
////    {"endpoint":"TEST",
////            "type":"POST",
////            "requestparams":{
////        "test1":"string",
////                "test1":"string"
////    },
////        "responseparams":{
////        "test2":"string",
////                "test1":"string"
////    }
////    }

    @PostMapping("/addApi")
    public SaveNewApiObj add(@RequestBody SaveNewApiBean saveNewApiBean){
        System.out.println(saveNewApiBean.getEndpoint());
        System.out.println(saveNewApiBean.getRequestparams());
        SaveNewApiObj DAObean =new SaveNewApiObj();
        DAObean.setUrl(saveNewApiBean.getEndpoint());
        DAObean.setType(saveNewApiBean.getType());

        DAObean.setParameters(genrateListService.convertSetToList_parameter(saveNewApiBean.getRequestparams().keySet()));
        DAObean.setResponseAttributes(genrateListService.convertSetToList_attribute(saveNewApiBean.getResponseparams().keySet()));
        return saveNewApiObjRepository.save(DAObean);
//
//        System.out.println(DAObean.getResponseAttributes().size());
//        return null;
    }

//    json Object model for reference
//    {"endpoint":"TEST",
//            "type":"POST",
//            "call_list":{
//        "1":{
//            "1":["test1"],
//            "2":["test2"]
//        }
//    }
//    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterApiTemplateBean registerApiTemplateBean){
        System.out.println("logging from index controller /register line 71");
        System.out.println(registerApiTemplateBean.getCall_list().get("1").get("1"));
        System.out.println(registerApiTemplateBean.getCall_list());
        RegisterNewApiObject registerNewApiObject =new RegisterNewApiObject();
        registerNewApiObject.setNew_endpoint(registerApiTemplateBean.getEndpoint());
        registerNewApiObject.setType(registerApiTemplateBean.getType());
        for (String id:registerApiTemplateBean.getCall_list().keySet()) {
            
        }


        return "ok";
    }

    @PostMapping("/test")
    public Responsebean test(@RequestBody HashMap<String,String> requestBody ){
        System.out.println("test route called --> index controller line 74 ");

        System.out.println("index controller line 77 "+requestBody.keySet());


        Responsebean resp =new Responsebean();
        HashMap<String,String> respmap =new HashMap<String, String>();
        respmap.put("name1","hello");
        resp.setValue(respmap);
        return resp;
    }

    @PostMapping("/test2")
    public Responsebean test2(@RequestBody HashMap<String,String> requestBody){
        System.out.println("test route called --> index controller line 84 ");
        System.out.println(requestBody.keySet());
        Responsebean resp =new Responsebean();
        HashMap<String,String> respmap =new HashMap<String, String>();
        respmap.put("name1","hello from test2");
        resp.setValue(respmap);
        return resp;
    }

    public Field GetAttributeValue(Object o, String FiledName) throws NullPointerException{
        Class<?> clazz = o.getClass();

        for(Field field : clazz.getDeclaredFields()) {
            //you can also use .toGenericString() instead of .getName(). This will
            //give you the type information as well.

            if(field.getName().equals(FiledName)){
                return field;
            }
            System.out.println("logging from index controller line 106 ");
            LinkedHashMap resultmap =new LinkedHashMap(Integer.parseInt(field.toGenericString()));

        }
        return null;
    }

}
