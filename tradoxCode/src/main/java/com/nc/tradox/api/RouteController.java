package com.nc.tradox.api;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.impl.InfoDataImpl;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Set;

@RequestMapping("api/v1/route")
@RestController
public class RouteController {

    private final TradoxService tradoxService;

    @Autowired
    public RouteController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @GetMapping
    public Route getRoute(@RequestParam String destinationId, HttpSession session) {
        if (!(Boolean) session.getAttribute("authorized")) {
            return null;
        }
        Route route = tradoxService.getRoute((String) session.getAttribute("userId"), destinationId);
        session.setAttribute("currentRoute", route);
        return route;
    }

    @GetMapping("/getData")
    public InfoData getRouteData(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            int userId = (int) session.getAttribute("userId");
            Country location = tradoxService.getUserById(userId).getLocation();
            String selectedCountryFullName = (String) session.getAttribute("selectedCountry");
            Country selectedCountry = tradoxService.getCountryByFullName(selectedCountryFullName);
            if (location != null && selectedCountry != null) {
                return tradoxService.getInfoData(location.getShortName(), selectedCountry.getShortName());
            }
        }
        return new InfoDataImpl();
    }

    @GetMapping("/test")
    public boolean test(HttpSession session) {
        session.setAttribute("userId", 1);
        session.setAttribute("selectedCountry", "Canada");
        return true;
    }

    @GetMapping("/save")
    public Boolean saveRoute(HttpSession session) {
        Route r = (Route) session.getAttribute("currentRoute");
        int userId = (int) session.getAttribute("userId");
        return tradoxService.saveRoute(r, userId);
    }

    @PostMapping("/editTransits")
    public void editTransits(@RequestBody Set<InfoData> transits, HttpSession session) {
        Integer routeId = ((Route) session.getAttribute("currentRoute")).getElementId();
        tradoxService.editTransits((int) session.getAttribute("userId"), routeId, transits);
    }

}