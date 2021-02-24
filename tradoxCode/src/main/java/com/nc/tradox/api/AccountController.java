package com.nc.tradox.api;

import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.InfoDataImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public UserData getUserData(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (isValidUser(userId)) {
            User user = tradoxService.getUserById(userId);
            return new UserData(user);
        }
        return null;
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
                    return "{\"result\": " + tradoxService.updateUser(user) + "}";
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
    public Boolean deleteRoute(@RequestBody Map<String, Integer> json, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            if (json.get("routeId") != null) {
                return tradoxService.deleteRoute(json.get("routeId"));
            }
        }
        return false;
    }

    @PostMapping("/getSavedRoute")
    public InfoData goToRoute(@RequestBody Map<String, Integer> json, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            Integer id = json.get("routeId");
            if (id != null) {
                Route route = tradoxService.getRouteById(id);
                if (route != null) {
                    List<InfoData> list = new ArrayList<>(route.getTransit());
                    return list.get(0);
                }
            }
        }
        return new InfoDataImpl();
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
