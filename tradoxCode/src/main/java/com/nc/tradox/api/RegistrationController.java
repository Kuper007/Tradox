package com.nc.tradox.api;

import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("api/v1/registration")
@RestController
public class RegistrationController {
    private final TradoxService tradoxService;
    Map<String, Boolean> resultJson;
    @Autowired
    public RegistrationController(TradoxService tradoxService){
        this.tradoxService = tradoxService;
    }

    @PostMapping("/fill")
    public Boolean registrarion(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession httpSession){
        if(!bindingResult.hasErrors()){
            Boolean result = tradoxService.registerUser(json);
            if(result){
                resultJson.put("result", true);
                return true;
            }
            else {
                resultJson.put("result", false);
                return false;
            }
        }else{
            resultJson.put("result", false);
            return false;
        }
    }
}
