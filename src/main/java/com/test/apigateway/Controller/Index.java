package com.test.apigateway.Controller;

import com.test.apigateway.Beans.RegisterApiTemplateBean;
import com.test.apigateway.Beans.SaveNewApiBean;
import com.test.apigateway.DAO.*;
import com.test.apigateway.Beans.Responsebean;
import com.test.apigateway.Repositories.RegisterNewApiObjectRepository;
import com.test.apigateway.Repositories.SaveNewApiObjRepository;
import com.test.apigateway.Services.CommonService;
import com.test.apigateway.Services.GenrateListService;
import com.test.apigateway.Services.GetUrlEndpointService;

import com.test.apigateway.Services.RegisterVerifyService;


import org.hibernate.HibernateException;

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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class Index {
    @Autowired
    SaveNewApiObjRepository saveNewApiObjRepository;
    @Autowired
    RegisterNewApiObjectRepository registerNewApiObjectRepository;

    @Autowired
    GenrateListService genrateListService;

    @Autowired
    GetUrlEndpointService getUrlEndpointService;
    
    @Autowired
    RegisterVerifyService registerVerifyService;

    @Autowired
    CommonService commonService;

    @GetMapping("/query/{endpoint}")
    public RequestEntity<Object> resolve(@PathVariable String endpoint, RequestEntity<Object> req_object){
       return null;

    }

    @PostMapping("/query/{endpoint}")
    public ResponseEntity<Responsebean> resolve(@RequestBody HashMap<String,String> req_object,@PathVariable String endpoint){

        System.out.println("index controller line 52 "+req_object.keySet());
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
    public  ResponseEntity<String> add(@RequestBody SaveNewApiBean saveNewApiBean){
        System.out.println(saveNewApiBean.getEndpoint());
        System.out.println(saveNewApiBean.getRequestparams());
        
        SaveNewApiObj registeredApi = saveNewApiObjRepository.findByUrlAndType(saveNewApiBean.getEndpoint(), saveNewApiBean.getType());
        System.out.println("api " + registeredApi);
        if(registeredApi != null) {
        	return ResponseEntity.ok().body("Already regitered url");
        }
        else {
        	
        	List<Parameter> requestParams = genrateListService.convertSetToListparameterWithType(saveNewApiBean.getRequestparams());
            
        	int status = registerVerifyService.validateApi(saveNewApiBean.getEndpoint(), saveNewApiBean.getType(), requestParams, saveNewApiBean.getRequestValues());
            System.out.println("status "+ status);
        	
        	if(status == 400) {
        		return ResponseEntity.ok().body("Bad Request");
        	}
        	else {
        		SaveNewApiObj DAObean = new SaveNewApiObj();
                DAObean.setUrl(saveNewApiBean.getEndpoint());
                DAObean.setType(saveNewApiBean.getType());
                DAObean.setParameters(requestParams);
                DAObean.setResponseAttributes(genrateListService.convertSetToListattributeWithType(saveNewApiBean.getResponseparams()));
                saveNewApiObjRepository.save(DAObean);
                return ResponseEntity.ok().body("Successfully register");
        	}
        }
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
//        System.out.println("logging from index controller /register line 71");
//        System.out.println(registerApiTemplateBean.getCall_list().get("1").get("1"));
//        System.out.println(registerApiTemplateBean.getCall_list());
        RegisterNewApiObject registerNewApiObject =new RegisterNewApiObject();
        registerNewApiObject.setNewEndpoint(registerApiTemplateBean.getEndpoint());
        registerNewApiObject.setType(registerApiTemplateBean.getType());
        List<QueryEndpoint> queryobjects =new ArrayList<>();


        for (String id:registerApiTemplateBean.getCall_list().keySet()) {
//            get savenewApiObject from service for the given id and add its attributes to the Queryendpoint object
            SaveNewApiObj saveNewApiObj=saveNewApiObjRepository.getOne(Integer.parseInt(id));
            QueryEndpoint queryEndpoint =new QueryEndpoint();
            queryEndpoint.setEndpoint(saveNewApiObj.getUrl());
            queryEndpoint.setParameters(saveNewApiObj.getParameters());
            queryEndpoint.setType(saveNewApiObj.getType());
            List<ResponseAttribute> required_responses =new ArrayList<>();
            for (String[] key:registerApiTemplateBean.getCall_list().get(id).values()
                 ) {
                required_responses.add(new ResponseAttribute(key[0]));
            }
            queryEndpoint.setResponse_attribs(required_responses);
            queryobjects.add(queryEndpoint);
        }
        registerNewApiObject.setQueryEndpoints(queryobjects);
        System.out.println(registerNewApiObject.getQueryEndpoints());
        try{
            registerNewApiObjectRepository.save(registerNewApiObject);
        }catch (HibernateException e){
            e.printStackTrace();
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

    @PostMapping("/test1")
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
    
    //Test url
    @GetMapping("/test/{id}/{name}")
	private ResponseEntity<String> testExistAPI( @PathVariable("id") String id, @PathVariable("name") String name) {
		
//		List<String> response = new ArrayList<String>();
		String test = "Test is successful "+id+" "+name;
		System.out.println("test");
		return ResponseEntity.ok().body(test);
	}

}
