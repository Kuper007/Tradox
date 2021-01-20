package com.nc.tradox.api;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.Status;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("api/v1/map")
@RestController
public class MapController {

    private final TradoxService tradoxService;

    @Autowired
    public MapController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/current_country")
    public RedirectView setCurrentCountry(@RequestBody String countryFullName, BindingResult bindingResult, HttpSession session) {
        session.setAttribute("result", 0);
        if (!bindingResult.hasErrors()) {
            Country country = tradoxService.getCountryByFullName(countryFullName);
            if (country != null) {
                if (session.getAttribute("userId") != null) {
                    int id = (int) session.getAttribute("userId");
                    tradoxService.getUserById(id).setLocation(country);
                    session.setAttribute("result", 1);
                } else {
                    session.setAttribute("result", 2);
                }
            }
        }
        return new RedirectView("api/v1/map/result");
    }

    @GetMapping("/result")
    public String getResult(HttpSession session) {
        int result = (int) session.getAttribute("result");
        if (result == 0) {
            return "Unfortunately, no changes have been made";
        } else if (result == 1) {
            return "Changes were successfully made";
        } else {
            return "Please register or log in if you already have an account";
        }
    }

    @PostMapping("/citizenship")
    public RedirectView setCitizenship(@RequestBody String countryFullName, BindingResult bindingResult, HttpSession session) {
        session.setAttribute("result", 0);
        if (!bindingResult.hasErrors()) {
            Country country = tradoxService.getCountryByFullName(countryFullName);
            if (country != null) {
                if (session.getAttribute("userId") != null) {
                    int id = (int) session.getAttribute("userId");
                    tradoxService.getUserById(id).getPassport().setCitizenshipCountry(country);
                    session.setAttribute("result", 1);
                } else {
                    session.setAttribute("result", 2);
                }
            }
        }
        return new RedirectView("api/v1/map/result");
    }

    @GetMapping("/search_result")
    public Map<String, Status.StatusEnum> getCountries(@RequestParam("search") String search, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            int id = (int) session.getAttribute("userId");
            String countryId = tradoxService.getUserById(id).getLocation().getShortName();
            return tradoxService.getCountriesWhereNameLike(countryId, search);
        } else {
            return null;
            // TODO: определять страну по геолокации
        }
    }
}