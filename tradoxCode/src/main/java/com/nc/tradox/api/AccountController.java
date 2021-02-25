package com.nc.tradox.api;

import com.nc.tradox.model.*;
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
    public Boolean saveUserData(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession session) throws ParseException {
        System.out.println("pipets");
        if (!bindingResult.hasErrors()) {
            System.out.println("loh");
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                Country citizenship = tradoxService.getCountryById(json.get("citizenship"));
                Country location = tradoxService.getCountryById(json.get("country_id"));
                Passport passport = new PassportImpl(json.get("passport_id"), citizenship);
                User user = tradoxService.getUserById(userId);
                user.setFirstName(json.get("first_name"));
                user.setLastName(json.get("last_name"));
                user.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(json.get("birth_date")));
                user.setEmail(json.get("email"));
                user.setPhone(json.get("phone"));
                user.setPassport(passport);
                user.setLocation(location);
                System.out.println(user.getPassportId());
                return tradoxService.updateUser(user);
            }
        }
        return false;
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
