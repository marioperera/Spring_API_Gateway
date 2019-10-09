package com.test.apigateway.Services;

import com.test.apigateway.DAO.Parameter;
import com.test.apigateway.DAO.ResponseAttribute;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
}
