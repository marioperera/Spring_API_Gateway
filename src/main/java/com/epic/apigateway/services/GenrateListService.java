package com.epic.apigateway.services;

import com.epic.apigateway.dao.Parameter;
import com.epic.apigateway.dao.ResponseAttribute;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mario_p
 * @Date 10/9/2019
 */
@Service
public class GenrateListService {
    public List<Parameter> convertSetToList;

    public   List<Parameter> convertSetToList_parameter(Set<String> set)
    {
        // create an empty list
        Set<Parameter> parms =new HashSet<>();
        for (String name:set
             ) {
            parms.add(new Parameter(name));
        }
        List<Parameter> list = new ArrayList(parms);
        return list;

        // push each element in the set into the list

    }

    public   List<ResponseAttribute> convertSetToList_attribute(Set<String> set)
    {
        // create an empty list
        Set<ResponseAttribute> parms =new HashSet<>();
        for (String name:set
        ) {
            parms.add(new ResponseAttribute(name));
        }
        List<ResponseAttribute> list = new ArrayList(parms);
        return list;

        // push each element in the set into the list

    }
    
    public List<Parameter> convertSetToListparameterWithType(HashMap<String, String> parameters)
    {
        // create an empty list
        Set<Parameter> parms =new HashSet<>();
        
        for (Map.Entry param : parameters.entrySet()) {
            parms.add(new Parameter(String.valueOf(param.getKey()), String.valueOf(param.getValue())));
        }
        List<Parameter> list = new ArrayList(parms);
        return list;

        // push each element in the set into the list

    }
    
    public   List<ResponseAttribute> convertSetToListattributeWithType(HashMap<String, String> parameters)
    {
        // create an empty list
        Set<ResponseAttribute> parms =new HashSet<>();
        for (Map.Entry param: parameters.entrySet()) {
            parms.add(new ResponseAttribute(String.valueOf(param.getKey()), String.valueOf(param.getValue())));
        }
        List<ResponseAttribute> list = new ArrayList(parms);
        return list;

        // push each element in the set into the list

    }
}
