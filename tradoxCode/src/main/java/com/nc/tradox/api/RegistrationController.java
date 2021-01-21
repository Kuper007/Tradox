package com.nc.tradox.api;

import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.script.ScriptEngine;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("api/v1/registration")
@RestController
public class RegistrationController {
    private final TradoxService tradoxService;

    @Autowired
    public RegistrationController(TradoxService tradoxService){
        this.tradoxService = tradoxService;
    }

    @PostMapping("/fill")
    public RedirectView registrarion(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession httpSession){
        if(!bindingResult.hasErrors()){
            Boolean result = tradoxService.registerUser(json);
            if(result){
                httpSession.setAttribute("registered",true);
            }
            else {
                httpSession.setAttribute("registered",false);
            }
        }else{
            httpSession.setAttribute("registered",false);
        }
        return new RedirectView("api/vi/registration/result");
    }

    @GetMapping("/registered")
    public Boolean getRegistrationResult(HttpSession httpSession){
        Boolean isRegistered  = (Boolean) httpSession.getAttribute("registered");
        if(isRegistered){
            return true;
        }
        return false;
    }
}
