package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.Response;
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
    public String auth(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            if (!checkAuthStatus(session)) {
                Response response = tradoxService.auth(json.get("email"), json.get("password"));
                User user = (User) response.getObject();
                String error = response.getError();
                if (error.equals("")) {
                    if (user != null) {
                        session.setAttribute("authorized", true);
                        session.setAttribute("userId", user.getUserId());
                        session.setAttribute("userType", user.getUserType());
                    }
                } else {
                    session.setAttribute("error", error);
                }
            } else {
                session.setAttribute("error", "alreadyAuthorized");
            }
        } else {
            session.setAttribute("error", "network");
        }
        return getAuthResult(session);
    }

    public String getAuthResult(HttpSession session) {
        Boolean isAuthorized = (Boolean) session.getAttribute("authorized");
        String json;
        if (isAuthorized) {
            Integer userId = (Integer) session.getAttribute("userId");
            String user = "\"" + userId + "\"";
            json = "{\"res\": \"true\",\"userId\": " + user + "}";
            return json;
        } else {
            String error = (String) session.getAttribute("error");
            error = "\"" + error + "\"";
            json = "{\"res\": " + error + "}";
        }
        return json;
    }

    private boolean checkAuthStatus(HttpSession session) {
        Boolean isAuthorized = (Boolean) session.getAttribute("authorized");
        if (isAuthorized == null) {
            session.setAttribute("authorized", false);
            return false;
        }
        if (isAuthorized) {
            session.setAttribute("error", "alreadyAuthorized");
            return true;
        }
        return false;
    }

}
