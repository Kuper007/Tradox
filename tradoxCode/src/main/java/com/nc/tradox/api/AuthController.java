package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final TradoxService tradoxService;

    @Autowired
    public AuthController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody Credentials credentials, BindingResult bindingResult, HttpSession session) {
        if(!bindingResult.hasErrors()) {
            Map<User,String> info = tradoxService.auth(credentials.getEmail(),credentials.getPassword());
            Map.Entry<User,String> entry = info.entrySet().iterator().next();
            User user = entry.getKey();
            String res = entry.getValue();
            if (user != null) {
                session.setAttribute("authorized",true);
                session.setAttribute("userId",user.getUserId());
                session.setAttribute("userType",user.getUserType());
            } else {
                session.setAttribute("authorized",false);
                session.setAttribute("error",res);
            }
        } else {
            session.setAttribute("authorized",false);
            session.setAttribute("error","network");
        }
        return getAuthResult(session);
    }


    public String getAuthResult(HttpSession session) {
        Boolean isAuthorized = (Boolean) session.getAttribute("authorized");
        String json;
        if (isAuthorized) {
            int userId = (int) session.getAttribute("userId");
            String user = "\""+userId+"\"";
            String userType = "\""+session.getAttribute("userType") +"\"";
            json = "{\"res\":\"true\",\"userId\":"+user+",\"userType\":"+userType+"}";
            return json;
        } else {
            String error = (String) session.getAttribute("error");
            error = "\""+error+"\"";
            json = "{\"res\":"+error+"}";
        }
        return json;
    }
}
