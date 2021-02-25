package com.nc.tradox.api;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.impl.InfoDataImpl;
import com.nc.tradox.model.impl.RouteImpl;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.service.TradoxService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RequestMapping("api/v1/route")
@RestController
public class RouteController {

    private final TradoxService tradoxService;

    @Autowired
    public RouteController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/setSelectedCountry")
    public void setCurrentCountry(@RequestBody Map<String, String> json, BindingResult bindingResult, HttpSession session) {
        if (!bindingResult.hasErrors()) {
            String fullName = json.get("countryName");
            if (tradoxService.isCountry(fullName)) {
                session.setAttribute("selectedCountry", fullName);
            }
        }
    }

    @GetMapping("/getData")
    public InfoData getRouteData(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            int userId = (int) session.getAttribute("userId");
            Country location = tradoxService.getUserById(userId).getLocation();
            String selectedCountryFullName = (String) session.getAttribute("selectedCountry");
            Country selectedCountry = tradoxService.getCountryByFullName(selectedCountryFullName);
            if (location != null && selectedCountry != null) {
                return tradoxService.getInfoData(location, selectedCountry);
            }
        }
        return new InfoDataImpl();
    }

    @GetMapping("/save")
    public Boolean saveRoute(HttpSession session) {
        Route r = (Route) session.getAttribute("currentRoute");
        if (r != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                return tradoxService.saveRoute(r, userId);
            }
        }
        return false;
    }

    @PostMapping("/getCountryInfo")
    public Response getCountryInfo(@RequestBody Map<String, String> json, HttpSession httpSession) {
        Response response = new Response();
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (isValidUser(userId)) {
            Country location = tradoxService.getUserLocation(userId);
            if (location == null) {
                response.setError("userLocationNotFound");
                response.setObject(new InfoDataImpl());
                return response;
            }
            Country destination = tradoxService.getCountryById(json.get("countryName"));
            if (destination == null) {
                response.setError("destinationNotFound");
                response.setObject(new InfoDataImpl());
                return response;
            }
            InfoData infoData = tradoxService.getInfoData(location, destination);
            Random random = new Random(System.currentTimeMillis());
            int id = random.nextInt();
            Set<InfoData> transits = new LinkedHashSet<>();
            transits.add(infoData);
            httpSession.setAttribute("currentRoute", new RouteImpl(id, transits));
            response.setObject(infoData);
        } else {
            response.setError("notAuthorized");
            response.setObject(new InfoDataImpl());
        }
        return response;
    }

    private boolean isValidUser(Integer userId) {
        if (userId != null) {
            return userId >= 0;
        }
        return false;
    }

    /*@PostMapping("/editTransits")
    public void editTransits(@RequestBody Set<InfoData> transits, HttpSession session) {
        Integer routeId = ((Route) session.getAttribute("currentRoute")).getRouteId();
        tradoxService.editTransits((int) session.getAttribute("userId"), routeId, transits);
    }*/

    @PostMapping("/getRoute")
    public Route getRoute(@RequestBody String json, HttpSession session) {
        if (json != null) {
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                Set<InfoData> transits = new LinkedHashSet<>();
                List<String> list = new LinkedList<>();
                JSONArray countries = new JSONArray(json);
                for (Object o : countries) {
                    String country = (String) o;
                    list.add(country);
                }
                Country firstCountry = tradoxService.getUserLocation(userId);
                for (String s : list) {
                    Country secondCountry = tradoxService.getCountryByFullName(s);
                    transits.add(tradoxService.getInfoData(firstCountry, secondCountry));
                    firstCountry = secondCountry;
                }
                Random random = new Random(System.currentTimeMillis());
                int id = random.nextInt();
                Route route = new RouteImpl(id, transits);
                session.setAttribute("currentRoute", route);
                return route;
            }
        }
        return null;
    }
}