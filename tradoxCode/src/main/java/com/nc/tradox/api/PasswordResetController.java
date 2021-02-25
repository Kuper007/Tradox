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

@RequestMapping("api/v1/reset")
@RestController
public class PasswordResetController {

    private final TradoxService tradoxService;
    private final EmailController emailController = new EmailController();

    @Autowired
    public PasswordResetController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/mail")
    public String reset(@RequestBody Map<String,String> map, BindingResult bindingResult, HttpSession session) {
        String json = "{\"res\":\"false\"}";
        if(!bindingResult.hasErrors()){
            String password = tradoxService.resetPassword(map.get("email"));
            if (!password.equals("")) {
                String text = "Here is your new password:\n"+password+"\nYou can change it later in your account settings.";
                boolean res = sendMail(map.get("email"),"Reset password",text);
                if (res) {
                    json = "{\"res\":\"true\"}";
                }
            }
        }

        return json;
    }

    public Boolean sendMail(String email, String subject, String text){
        boolean res = false;
        try {
            emailController.sendMail(email,subject,text);
            res = true;
        } catch (Exception e) {

        }
        return res;
    }

}


