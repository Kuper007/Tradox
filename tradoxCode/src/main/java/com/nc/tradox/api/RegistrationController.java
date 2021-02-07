package com.nc.tradox.api;

import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("api/v1/registration")
@RestController
public class RegistrationController {
    private final TradoxService tradoxService;

    @Autowired
    public RegistrationController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/fill")
    public String registration(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession httpSession) {
        if (!bindingResult.hasErrors()) {
            return tradoxService.registerUser(json,httpSession);
        }
        return "{\"result\": false, \"emailNotUnique\": false, \"passportNotUnique\": false}";
    }

}
