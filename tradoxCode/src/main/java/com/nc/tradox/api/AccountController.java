package com.nc.tradox.api;

import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.PassportImpl;
import com.nc.tradox.model.impl.UserImpl;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/api/v1/account")
@RestController
public class AccountController {

    private final TradoxService tradoxService;

    @Autowired
    public AccountController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @GetMapping("/getUserData")
    public User getUserData(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (isValidUser(userId)) {
            return tradoxService.getUserById(userId);
        }
        return new UserImpl();
    }

    @PostMapping("/saveUserData")
    public String saveUserData(@RequestBody UserData userData, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            try {
                Integer userId = (Integer) session.getAttribute("userId");
                if (userId != null) {
                    User user = new UserImpl();
                    user.setUserId(userId);
                    user.setFirstName(userData.getFirstName());
                    user.setLastName(userData.getLastName());
                    user.setBirthDate(new SimpleDateFormat("dd.MM.yyyy").parse(userData.getBirthDate()));
                    user.setEmail(userData.getEmail());
                    user.setPhone(userData.getPhone());
                    user.setLocation(tradoxService.getCountryByFullName(userData.getLocation()));
                    user.setPassport(new PassportImpl(
                            userData.getPassport(),
                            tradoxService.getCountryByFullName(userData.getCitizenship()))
                    );
                    return "{\"result\": " + tradoxService.updateUserData(user) + "}";
                } else {
                    Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "User not authorized");
                }
            } catch (ParseException e) {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Incorrect date format");
            }
        }
        return "{\"result\": false}";
    }

    @GetMapping("/delete")
    public Boolean deleteAccount(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            return tradoxService.deleteUser(userId);
        } else {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "User not authorized");
        }
        return false;
    }

    @PostMapping("/deleteRoute")
    public Boolean deleteRoute(@RequestBody String destination, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                String destinationId = tradoxService.getCountryByFullName(destination).getShortName();
                Route route = tradoxService.getRoute(String.valueOf(userId), destinationId);
                return tradoxService.deleteRoute(route);
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "User not authorized");
            }
        }
        return false;
    }

    @PostMapping("/toRoute")
    public RedirectView goToRoute(@RequestBody String destination, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                String destinationId = tradoxService.getCountryByFullName(destination).getShortName();
                Route route = tradoxService.getRoute(String.valueOf(userId), destinationId);
                if (route != null) {
                    session.setAttribute("currentRoute", route);
                    return new RedirectView("api/v1/route");
                }
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "User not authorized");
            }
        }
        return null;
    }

    @GetMapping("/logout")
    public RedirectView logOut(HttpSession session) {
        session.setAttribute("authorized", false);
        session.removeAttribute("userId");
        return new RedirectView("/api/v1/auth/result");
    }

    private boolean isValidUser(Integer userId) {
        if (userId != null) {
            return userId >= 0;
        }
        return false;
    }

}
