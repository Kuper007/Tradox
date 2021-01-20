package com.nc.tradox.api;

import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@RequestMapping("/api/v1/account")
@RestController
public class AccountController {

    private final TradoxService tradoxService;

    @Autowired
    public AccountController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/saveUserData")
    public Boolean saveUserData(@RequestBody User user, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            return tradoxService.updateUser(user);
        }
        return false;
    }

    @PostMapping("/delete")
    public Boolean deleteAccount(@RequestBody User user, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            return tradoxService.deleteUser(user);
        }
        return false;
    }

    @PostMapping("/delete/route")
    public Boolean deleteRoute(@RequestBody Route route, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            return tradoxService.deleteRoute(route);
        }
        return false;
    }

    @PostMapping("/toRoute")
    public void goToRoute(@RequestBody Route route, BindingResult bindingResult, HttpSession session) {

    }

    @GetMapping("/logout")
    public RedirectView logOut(HttpSession session) {
        session.setAttribute("authorized", false);
        session.removeAttribute("userId");
        return new RedirectView("/api/v1/auth/result");
    }

}
