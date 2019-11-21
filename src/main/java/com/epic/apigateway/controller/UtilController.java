package com.epic.apigateway.controller;

import com.epic.apigateway.dao.ApplicationUser;
import com.epic.apigateway.repositories.ApplicationUserRepository;
import com.epic.apigateway.repositories.SaveNewApiObjRepository;
import com.epic.apigateway.dao.SaveNewApiObj;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mario_p
 * @Date 10/11/2019
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/Utils")
public class UtilController {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SaveNewApiObjRepository saveNewApiObjRepository;

    public UtilController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder, SaveNewApiObjRepository saveNewApiObjRepository) {
        this.applicationUserRepository =applicationUserRepository;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;

        this.saveNewApiObjRepository = saveNewApiObjRepository;
    }


    @GetMapping("/getregistedURLs")
    public List<SaveNewApiObj> getURLs(){
        System.out.println("get request url mapping called");
        return saveNewApiObjRepository.findAll();
    }

    @PostMapping("/sign_up")
    public ApplicationUser signup( @RequestBody ApplicationUser user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return applicationUserRepository.save(user);
    }

}
