package com.epic.apigateway.services;

import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.QueryEndpoint;
import com.epic.apigateway.dao.ResponseAttribute;
import com.epic.apigateway.beans.Responsebean;
import com.epic.apigateway.utils.RestTemplateConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.logging.Logger;


/**
 * @author mario_p
 * @Date 10/4/2019
 */


@Service
public class CommonService {

    @Autowired
    RestTemplateConfig restTemplateConfig;

    Map<String, Object> get_response(HashMap<String, String> req_object, List<QueryEndpoint> endpoints, HashMap<String, String> headers) throws Exception {

        HashSet<String> REQUEST_PARAMETER_LIST = new HashSet<>();

        HashSet<String> PathVariables = new HashSet<>();


        for (QueryEndpoint endpoint : endpoints
        ) {
            endpoint.getParameters().forEach(P -> {

                if (P.getMandatory()) {
                    REQUEST_PARAMETER_LIST.add(P.getParamname());

                }
                if (P.getType().equals("path")) {

                    PathVariables.add(P.getParamname());
                }


            });
        }
        System.out.println(req_object + " request object ");
        System.out.println("Path Variables " + PathVariables + "\n Request parameters " + REQUEST_PARAMETER_LIST);


        Map<String, Object> request;
        request = new HashMap<>();
        for (String request_param : REQUEST_PARAMETER_LIST
        ) {
            if (!req_object.containsKey(request_param) && REQUEST_PARAMETER_LIST.size() > 0) {
                throw new Exception("DOES NOT CONTAIN PARAMETER ID " + request_param);
            } else {
                for (QueryEndpoint endpoint : endpoints
                ) {
                    String url = endpoint.getEndpoint();

                    url = SetPathVariables(url, PathVariables, req_object);

                    Logger.getAnonymousLogger().info("Modified url is " + url);

                    endpoint.setEndpoint(url);

                    Responsebean response = new Responsebean();

                    String RECEIVED_RESPONSE;
                    try {
                        RECEIVED_RESPONSE = make_rest_request(endpoint, req_object);
                        System.out.println("recieved response form " + url + " \n" + RECEIVED_RESPONSE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }


                    HashMap<String, String> response_map = new HashMap<>();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> map = mapper.readValue(RECEIVED_RESPONSE, Map.class);
                        response.setValue(map);


                        map.forEach((k, v) -> {
                            String vv = String.valueOf(v);
                            response_map.put(k, vv);
                        });


                    } catch (Exception e) {
                        throw new Exception(url);
                    }

                    try {
                        if (endpoint.getResponse_attribs() != null) {
                            for (ResponseAttribute val : endpoint.getResponse_attribs()
                            ) {

                                String field = response_map.get(val.getAttribute());

                                assert field != null;

                                request.put(endpoint.getMappings().get(val.getAttribute()), response_map.get(val.getAttribute()));


                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("line 101" + request);

            }
        }
        return request;


    }
/**
* @param endpoint the current endpoint
* @param req_object the map of responses required by the user
* @return String(APPLICATION_JSON)
**/

    private String make_rest_request(QueryEndpoint endpoint, HashMap<String, String> req_object) throws Exception {
        StringBuilder endPointUrl = new StringBuilder(endpoint.getEndpoint());

        RestTemplate restTemplate = restTemplateConfig.restTemplate();
        String Type = endpoint.getType();

        List<Parameter> REQUEST_PARAMETERS;

        REQUEST_PARAMETERS = endpoint.getParameters();

        Map<String, String> params = new HashMap<>();

        Map<String, String> bodyData = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        String response = "";

        for (Parameter param : REQUEST_PARAMETERS
        ) {
            if (param.getType().equals("param")) {
                params.put(param.getParamname(), req_object.get(param.getParamname()));
            } else if (param.getType().equals("body") && !endpoint.getType().equals("GET")) {
                bodyData.put(param.getParamname(), req_object.get(param.getParamname()));
            } else if (param.getType().equals("header")) {
                headers.set(param.getParamname(), req_object.get(param.getParamname()));
            } else if (param.getType().equals("query")) {
                if (!endPointUrl.toString().contains("?")) {
                    endPointUrl.append("?").append(param.getParamname()).append("=").append(req_object.get(param.getParamname()));
                } else {
                    endPointUrl.append("&").append(param.getParamname()).append("=").append(req_object.get(param.getParamname()));
                }
                System.out.println("From header query : " + param.getParamname() + " : " + req_object.get(param.getParamname()));
            }
        }
        try {
            if (Type.equals("POST")) {
                HttpEntity<Object> entity = new HttpEntity<>(bodyData, headers);

                response = restTemplate.exchange(endPointUrl.toString(), HttpMethod.POST, entity, String.class, params).getBody();

            } else if (Type.equals("GET")) {
                HttpEntity<Object> entity = new HttpEntity<>(headers);

                System.out.println(endPointUrl.toString());
                response = restTemplate.exchange(endPointUrl.toString(), HttpMethod.GET, entity, String.class, params).getBody();

            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new Exception("problem reaching url " + endPointUrl);
        }

        return response;
    }
/**
 * @param url the url currently set on the request endpoint
 * @param path_variables the map of path variables resisted by the user
 * @param req_object the map of responses required by the user
 * @return String
 * **/

    private String SetPathVariables(String url, HashSet<String> path_variables, HashMap<String, String> req_object) {
        if (!url.contains("{")) {
            return url;
        } else {
            String new_Ret_url = url;
            System.out.println("got regex objects for request_obj Pathvariables =>" + path_variables);
            for (String Path_Variable : path_variables
            ) {
                String regex = "(\\{";
                regex += Path_Variable;
                regex += "})";
                new_Ret_url = url.replaceAll(regex, String.valueOf(req_object.get(Path_Variable)));
                System.out.println(new_Ret_url + " modified url");

            }

            return new_Ret_url;
        }

    }

}
