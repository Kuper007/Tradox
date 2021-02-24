package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("api/v1/verification")
@RestController
public class VerificationController {

    private final TradoxService tradoxService;
    private final EmailController emailController = new EmailController();

    @Autowired
    public VerificationController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/verify")
    public String verified(HttpSession session){
        String json = "{\"res\":\"false\"}";
        int userId = (int) session.getAttribute("userId");
        User user = tradoxService.getUserById(userId);
        if (user!=null){
            user.setVerify();
            boolean res = tradoxService.verifyUser(userId);
            if (res) {
                json = "{\"res\":\"true\"}";
            }
        }

        return json;
    }

    @PostMapping("/mail")
    public String sendMail(HttpSession session){
        String json = "{\"res\":\"false\"}";
        int userId = (int) session.getAttribute("userId");
        User user = tradoxService.getUserById(userId);
        if (user!=null){
            String email = user.getEmail();
            String code = new RandomString(12).nextString();
            String text = "Here is your verification code:\n"+code;
            boolean res = sendMail(email,"Verification",text);
            if (res) {
                json = "{\"res\":\"true\",\"code\":"+"\""+code+"\""+"}";
            }
        }

        return json;
    }

    public Boolean sendMail(String email, String subject, String text){
        boolean res = false;
        try {
            emailController.sendMail(email,subject,text);
            res = true;
        } catch (Exception e){

        }
        return res;
    }

}
