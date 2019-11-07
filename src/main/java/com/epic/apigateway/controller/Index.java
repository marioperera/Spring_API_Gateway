package com.epic.apigateway.controller;

import com.epic.apigateway.beans.CallListElement;
import com.epic.apigateway.dao.*;
import com.epic.apigateway.dao.Mapping;
import com.epic.apigateway.mongo.documents.SavenewApiDocument;
import com.epic.apigateway.mongo.mongorepository.ApiDocumentRepository;
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

import javax.servlet.http.HttpServletRequest;
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
    ApiDocumentRepository apiDocumentRepository;

    @Autowired
    CommonService commonService;

    @CrossOrigin("*")
    @GetMapping("/query/{endpoint}")
    public RequestEntity<Object> resolve(@PathVariable String endpoint, RequestEntity<Object> req_object) {
        return null;

    }

    @CrossOrigin("*")
    @PostMapping("/query/{endpoint}")
    public ResponseEntity<Responsebean> resolve(HttpServletRequest request, @RequestBody HashMap<String, String> req_object, @PathVariable String endpoint) {

        System.out.println("index controller line 52 " + req_object.keySet());
        ResponseEntity responseEntity;
        System.out.println(endpoint);
        try {
            List<QueryEndpoint> endpoints = getUrlEndpointService.GetURL(endpoint);
            Responsebean resp = new Responsebean();
//            System.out.println("-- from index controller line 43 --");
//            for (QueryEndpoint queryEndpoint:endpoints
//                 ) {
//                System.out.println(queryEndpoint.getEndpoint());
//            }
//            resp.setValue(commonService.get_response(req_object,endpoints));

            responseEntity = new ResponseEntity(resp, HttpStatus.OK);
            return responseEntity;

        } catch (Exception e) {
            e.printStackTrace();
            Responsebean resp = new Responsebean();
            resp.setValue(e);
            responseEntity = new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> add(@RequestBody SaveNewApiBean saveNewApiBean) {

        //Check api already registered or not
        SaveNewApiObj registeredApi = saveNewApiObjRepository.findByUrlAndType(saveNewApiBean.getEndpoint(), saveNewApiBean.getType());
        System.out.println("api " + registeredApi);
        if (registeredApi != null) {
            return ResponseEntity.ok().body("Already registered url");
        } else {
            System.out.println(saveNewApiBean.getRequestValues());
            List<Parameter> requestParams = genrateListService.convertSetToListparameterWithType(saveNewApiBean.getRequestparams());

            int status = registerVerifyService.validateApi(saveNewApiBean.getEndpoint(), saveNewApiBean.getType(), requestParams, saveNewApiBean.getRequestValues());
            System.out.println("status " + status);

            if (status == 400) {
                return ResponseEntity.ok().body("Bad Request");
            } else {
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
//    "endpoint": "http://localhost:4001/api/query/xxx",
//        "type": "GET",
//        "call_list": {
//    "1": {
//        "response_attribs": [
//        "name2",
//                "output5"
//            ],
//        "mappings": {
//            "name2": "%$!!!!name",
//                    "output5": "%$output5"
//        }
//    },
//    "6":  {
//        "response_attribs": [
//        "name2","output5"
//            ],
//        "mappings": {
//            "name2": "%$name",
//                    "output5": "%$!!!output5"
//        }
//    }
//}
//}

    @CrossOrigin("*")
    @PostMapping("/register")
    public Responsebean register(@RequestBody RegisterApiTemplateBean registerApiTemplateBean) {
        System.out.println("logging from index controller /register line 71");
        System.out.println(registerApiTemplateBean.getCall_list().get("1"));
//        System.out.println(registerApiTemplateBean.getCall_list());
        RegisterNewApiObject registerNewApiObject = new RegisterNewApiObject();
        registerNewApiObject.setNewEndpoint(registerApiTemplateBean.getEndpoint());
        registerNewApiObject.setType(registerApiTemplateBean.getType());
        List<QueryEndpoint> queryobjects = new ArrayList<>();


        for (String id : registerApiTemplateBean.getCall_list().keySet()) {
//            get savenewApiObject from service for the given id and add its attributes to the Queryendpoint object
            SaveNewApiObj saveNewApiObj = saveNewApiObjRepository.getOne(Integer.parseInt(id));
            QueryEndpoint queryEndpoint = new QueryEndpoint();
            queryEndpoint.setEndpoint(saveNewApiObj.getUrl());
            queryEndpoint.setParameters(saveNewApiObj.getParameters());
            queryEndpoint.setType(saveNewApiObj.getType());
            List<ResponseAttribute> required_responses = new ArrayList<>();
//            todo : generate new code for mappings attribute
            Arrays.stream(registerApiTemplateBean.getCall_list().get(id).getResponse_attribs()).forEach(s -> {
                required_responses.add(new ResponseAttribute(s));
            });
            HashMap<String, String> user_request_mappings = registerApiTemplateBean.getCall_list().get(id).getMappings();
            queryEndpoint.setMappings(user_request_mappings);
            System.out.println("user_request mappings" + user_request_mappings);
            queryEndpoint.setResponse_attribs(required_responses);
            queryobjects.add(queryEndpoint);
        }
        registerNewApiObject.setQueryEndpoints(queryobjects);
        System.out.println(registerNewApiObject.getQueryEndpoints());
        Responsebean responsebean = new Responsebean();
        try {
            responsebean.setCode("ok");
//            responsebean.setValue(registerNewApiObjectRepository.save(registerNewApiObject));
            SavenewApiDocument savenewApiDocument = new SavenewApiDocument();
            savenewApiDocument.setEndpoints(registerNewApiObject.getQueryEndpoints());
            savenewApiDocument.setType(registerNewApiObject.getType());
            savenewApiDocument.setUrl(registerNewApiObject.getNewEndpoint());
            SavenewApiDocument endpoints = apiDocumentRepository
                    .getDistinctByUrl(registerNewApiObject.getNewEndpoint());
            if (endpoints == null) {
                apiDocumentRepository.save(savenewApiDocument);
            } else {
                throw new Exception("duplicate entries exists");
            }


        } catch (Exception e) {
            responsebean.setCode("error");
            responsebean.setValue(e.getMessage());
        }
        responsebean.setValue("ok");
        return responsebean;


    }

    @PostMapping("/test")
    public Map test(@RequestBody HashMap<String, String> requestBody) {
//        System.out.println("test route called --> index controller line 74 ");
//
//        System.out.println("index controller line 77 "+requestBody.keySet());


        Responsebean resp = new Responsebean();
        HashMap<String, String> respmap = new HashMap<String, String>();
        respmap.put("name1", "hello /test");
        respmap.put("name2", "hello2 /test");
        resp.setValue(respmap);
        return respmap;
    }

    @PostMapping("/test1")
    public Map test2(@RequestBody HashMap<String, String> requestBody) {
//        System.out.println("test route called --> index controller line 84 ");
//        System.out.println(requestBody.keySet());
        Responsebean resp = new Responsebean();
        HashMap<String, String> respmap = new HashMap<String, String>();
        respmap.put("name3", "hello from test2");
        respmap.put("name4", "hello /test2");
        resp.setValue(respmap);
        return respmap;
    }

    @GetMapping("/test3")
    public Map test3(@RequestParam String name2, @RequestParam String name1) {
        HashMap<String, String> resp = new HashMap<>();
        Responsebean responsebean = new Responsebean();
        if (name1 != null) {
            System.out.println("got path variable one " + name1);
            resp.put("output5", "value from pathvariable 1");
        }
        if (name2 != null) {
            System.out.println("got path variable two " + name2);
            resp.put("output6", "value from pathvariable 2");
        }
        responsebean.setValue(resp);
        return resp;
    }

    public Field GetAttributeValue(Object o, String FiledName) throws NullPointerException {
        Class<?> clazz = o.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            //you can also use .toGenericString() instead of .getName(). This will
            //give you the type information as well.

            if (field.getName().equals(FiledName)) {
                return field;
            }
            System.out.println("logging from index controller line 106 ");
            LinkedHashMap resultmap = new LinkedHashMap(Integer.parseInt(field.toGenericString()));

        }
        return null;
    }

    //Test url
    @GetMapping("/test/{id}/{name}")
    private ResponseEntity<String> testExistAPI(@PathVariable("id") String id, @PathVariable("name") String name) {

//		List<String> response = new ArrayList<String>();
        String test = "Test is successful " + id + " " + name;
        System.out.println(test);
        return ResponseEntity.ok().body(test);
    }

    @CrossOrigin("*")
    @PostMapping("/testRegister")
    private Responsebean registerNewApi(@RequestBody RegisterApiTemplateBean registerApiTemplateBean) {
        String endpoint = registerApiTemplateBean.getEndpoint();
        String type = registerApiTemplateBean.getType();

        RegisterNewApiObject registerNewApi = registerNewApiObjectRepository.findByNewEndpointAndType(endpoint, type);
        if (registerNewApi != null) {
            System.out.println("There is a already registered api");

            Responsebean responsebean = new Responsebean();
            responsebean.setCode("error");
            responsebean.setValue("Already registered Api");
            return responsebean;
        } else {
            System.out.println("There is no registered api");
            HashMap<String, CallListElement> endpointList = registerApiTemplateBean.getCall_list();

            List<QueryEndpoint> queryEndpoints = new ArrayList<QueryEndpoint>();

            for (String key : endpointList.keySet()) {
                SaveNewApiObj saveNewApiObj = saveNewApiObjRepository.findByUrl(key);
                QueryEndpoint queryEndpoint = new QueryEndpoint();
                queryEndpoint.setEndpoint(key);
                queryEndpoint.setType(saveNewApiObj.getType());
                queryEndpoint.setParameters(saveNewApiObj.getParameters());
                //queryEndpoint.setMappings(endpointList.get(key).getMappings());

                //SetMapping new mapping
//        		List<Mapping> mappings = new ArrayList<Mapping>();
//        		for(String paramName: endpointList.get(key).getMappings().keySet()) {
//        			Mapping map = new Mapping();
//        			map.setParamname(paramName);
//        			map.setMappingname(endpointList.get(key).getMappings().get(paramName));
//        			//map.setQueryEndpoint(queryEndpoint);
//        			mappings.add(map);
//        		}
//        		
//        		queryEndpoint.setMapping(mappings);

                String reponseAttributes[] = endpointList.get(key).getResponse_attribs();
                List<ResponseAttribute> responseList = new ArrayList<ResponseAttribute>();

                for (String reponseName : reponseAttributes) {
                    ResponseAttribute responseAttribute = new ResponseAttribute();
                    responseAttribute.setAttribute(reponseName);
                    responseList.add(responseAttribute);
                }
                queryEndpoint.setResponse_attribs(responseList);
                queryEndpoints.add(queryEndpoint);
            }
            RegisterNewApiObject registerNewApiObject = new RegisterNewApiObject();
            registerNewApiObject.setNewEndpoint(endpoint);
            registerNewApiObject.setQueryEndpoints(queryEndpoints);
            registerNewApiObject.setType(type);

            Responsebean responsebean = new Responsebean();
            try {
                responsebean.setCode("ok");
                responsebean.setValue(registerNewApiObjectRepository.save(registerNewApiObject));
            } catch (Exception e) {
                responsebean.setCode("error");
                responsebean.setValue(e.toString());
            }
            return responsebean;
        }
    }

    @PostMapping("/hsTest3/{uid}")
    private ResponseEntity<String> hsTest3(@PathVariable("uid") String uid, @RequestBody HashMap<String, String> requestBody) {

        String bodyOutput = "";
        for (String key : requestBody.keySet()) {
            bodyOutput = bodyOutput + " " + requestBody.get(key);
        }

        return ResponseEntity.ok().body(bodyOutput + " " + uid);
    }

    @GetMapping("/testParam")
    private ResponseEntity<String> testParameters(@RequestParam String queryId) {
        return ResponseEntity.ok().body("this is id " + queryId);
    }

}
