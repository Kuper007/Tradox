package com.nc.tradox.api;

import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;

@RequestMapping("api/v1/reset")
@RestController
public class PasswordResetController {

    private final TradoxService tradoxService;

    @Autowired
    public PasswordResetController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/mail")
    public String reset(@RequestBody String email, BindingResult bindingResult, HttpSession session) {
        String json = "\"res\":\"false\"";
        if(!bindingResult.hasErrors()){
            String password = tradoxService.resetPassword(email);
            if (!password.equals("")) {
                boolean res = sendMail(email,password);
                if (res) {
                    json = "\"res\":\"true\"";
                }
            }
        }

        return json;
    }

    public Boolean sendMail(String email, String password){
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
            message.setSubject("Reset password");
            message.setText("Here is your new password:\n"+password+"\nYou can change it later in your account settings.");
            Transport.send(message);
            res = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return res;
    }

}


