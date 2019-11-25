package com.epic.apigateway.controller;

import com.epic.apigateway.beans.Responsebean;
import com.epic.apigateway.dao.ApplicationUser;
import com.epic.apigateway.repositories.ApplicationUserRepository;
import com.epic.apigateway.repositories.SaveNewApiObjRepository;
import com.epic.apigateway.dao.SaveNewApiObj;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

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

    @PostMapping("/activate_user")
    public Responsebean Activate(@RequestBody ApplicationUser user){
        Logger.getAnonymousLogger().info("change status called on user "+user.getUsername());
        try {
            int res = applicationUserRepository.updateUserStatus("true",user.getUsername());
            if (res ==0){
                return new Responsebean("db_error","500");
            }else{
                return new Responsebean("ok","200");
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());

        }
        return null;
    }

}
