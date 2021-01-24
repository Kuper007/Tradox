package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingListener;
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
    public RedirectView auth(@RequestBody Credentials credentials, BindingResult bindingResult, HttpSession session) {
        if(!bindingResult.hasErrors()) {
            User user = tradoxService.auth(credentials.getEmail(),credentials.getPassword());
            if (user != null) {
                session.setAttribute("authorized",true);
                session.setAttribute("userId",user.getUserId());
            } else {
                session.setAttribute("authorized",false);
            }
        } else {
            session.setAttribute("authorized",false);
        }
        return new RedirectView("/api/v1/auth/result");
    }

    @GetMapping("/result")
    public Boolean getAuthResult(HttpSession session) {
        Boolean isAuthorized = (Boolean) session.getAttribute("authorized");
        if (isAuthorized) {
            return true;
        }
        return false;
    }
}
