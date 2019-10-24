package com.epic.apigateway.controller;

import com.epic.apigateway.dao.*;
import com.epic.apigateway.repositories.RegisterNewApiObjectRepository;
import com.epic.apigateway.repositories.SaveNewApiObjRepository;
import com.epic.apigateway.beans.RegisterApiTemplateBean;
import com.epic.apigateway.beans.SaveNewApiBean;
import com.epic.apigateway.beans.Responsebean;
import com.epic.apigateway.services.CommonService;
import com.epic.apigateway.services.GenrateListService;
import com.epic.apigateway.services.GetUrlEndpointService;

import com.epic.apigateway.services.RegisterVerifyService;


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

    @CrossOrigin("*")
    @GetMapping("/query/{endpoint}")
    public RequestEntity<Object> resolve(@PathVariable String endpoint, RequestEntity<Object> req_object){
       return null;

    }
    @CrossOrigin("*")
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
//    {"endpoint":"TEST",
//            "type":"POST",
//            "requestparams":{
//        "test1":"string",
//                "test1":"string"
//    },
//        "responseparams":{
//        "test2":"string",
//                "test1":"string"
//    }
//    }

    //Add api's
    @PostMapping("/addApi")
    public  ResponseEntity<String> add(@RequestBody SaveNewApiBean saveNewApiBean){
        
    	//Check api already registered or not
        SaveNewApiObj registeredApi = saveNewApiObjRepository.findByUrlAndType(saveNewApiBean.getEndpoint(), saveNewApiBean.getType());
        System.out.println("api " + registeredApi);
        if(registeredApi != null) {
        	return ResponseEntity.ok().body("Already registered url");
        }
        else {
            System.out.println(saveNewApiBean.getRequestValues());
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
//{
//    "endpoint": "http://localhost:4001/query/test",
//        "type": "POST",
//        "call_list": {
//    "1": [
//    "name2",
//            "name1"
//    ],
//    "6": [
//    "name2"
//    ]
//}
//}
    @CrossOrigin("*")
    @PostMapping("/register")
    public Responsebean register(@RequestBody RegisterApiTemplateBean registerApiTemplateBean){
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
            for (String key:registerApiTemplateBean.getCall_list().get(id)
                 ) {
                required_responses.add(new ResponseAttribute(key));
            }
            queryEndpoint.setResponse_attribs(required_responses);
            queryobjects.add(queryEndpoint);
        }
        registerNewApiObject.setQueryEndpoints(queryobjects);
        System.out.println(registerNewApiObject.getQueryEndpoints());
        Responsebean responsebean =new Responsebean();
        try{
            responsebean.setCode("ok");
            responsebean.setValue(registerNewApiObjectRepository.save(registerNewApiObject));

        }catch (Exception e){
            responsebean.setCode("error");
            responsebean.setValue(e.toString());
        }

        return responsebean;


    }

    @PostMapping("/test")
    public Responsebean test(@RequestBody HashMap<String,String> requestBody ){
        System.out.println("test route called --> index controller line 74 ");

        System.out.println("index controller line 77 "+requestBody.keySet());


        Responsebean resp =new Responsebean();
        HashMap<String,String> respmap =new HashMap<String, String>();
        respmap.put("name1","hello /test");
        respmap.put("name2","hello2 /test");
        resp.setValue(respmap);
        return resp;
    }

    @PostMapping("/test1")
    public Responsebean test2(@RequestBody HashMap<String,String> requestBody){
        System.out.println("test route called --> index controller line 84 ");
        System.out.println(requestBody.keySet());
        Responsebean resp =new Responsebean();
        HashMap<String,String> respmap =new HashMap<String, String>();
        respmap.put("name3","hello from test2");
        respmap.put("name4","hello /test2");
        resp.setValue(respmap);
        return resp;
    }

    @GetMapping("/test3")
    public Responsebean test3(@RequestParam String name2 ,@RequestParam String name1){
        HashMap<String,String> resp = new HashMap<>();
        Responsebean responsebean =new Responsebean();
        if(name1!=null){
            System.out.println("got path variable one "+name1);
            resp.put("output5","value from pathvariable 1");
        }
        if(name2!=null){
            System.out.println("got path variable two "+name2);
            resp.put("output6","value from pathvariable 2");
        }
        responsebean.setValue(resp);
        return responsebean;
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
		System.out.println(test);
		return ResponseEntity.ok().body(test);
	}

}