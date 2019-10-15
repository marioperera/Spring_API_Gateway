package com.test.apigateway.Controller;

import com.test.apigateway.DAO.SaveNewApiObj;
import com.test.apigateway.Repositories.SaveNewApiObjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mario_p
 * @Date 10/11/2019
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/Utils")
public class UtilController {
    @Autowired
    SaveNewApiObjRepository saveNewApiObjRepository;

    @GetMapping("/getregistedURLs")
    public List<SaveNewApiObj> getURLs(){
        System.out.println("get request url mapping called");
        return saveNewApiObjRepository.findAll();
    }

}
