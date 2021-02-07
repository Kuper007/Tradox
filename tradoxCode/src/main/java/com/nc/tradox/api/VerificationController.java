package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@RequestMapping("api/v1/verification")
@RestController
public class VerificationController {

    private final TradoxService tradoxService;

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
            boolean res = sendMail(email, code);
            if (res) {
                json = "{\"res\":\"true\",\"code\":"+"\""+code+"\""+"}";
            }
        }

        return json;
    }

    public Boolean sendMail(String email, String code){
        boolean res = false;
        String to = email;
        String from = "tradox@gmail.com";
        String host = "localhost";
        String port = "25";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Verification");
            message.setText("Here is your verification code:\n"+code);
            Transport.send(message);
            res = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return res;
    }

}
