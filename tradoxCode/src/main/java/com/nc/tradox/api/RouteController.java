package com.nc.tradox.api;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.FullRoute;
import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.impl.FullRouteImpl;
import com.nc.tradox.model.impl.InfoDataImpl;
import com.nc.tradox.model.impl.RouteImpl;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.service.TradoxService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
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
            if (location.getShortName().equals(destination.getShortName())) {
                response.setError("locationEqualsDestination");
                response.setObject(new InfoDataImpl());
                return response;
            }
            response.setObject(tradoxService.getInfoData(location, destination));
        } else {
            response.setError("notAuthorized");
            response.setObject(new InfoDataImpl());
        }
        return response;
    }

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

    @PostMapping("/saveRoute")
    public Response saveRoute(@RequestBody Map<String, String> json, HttpSession httpSession) {
        Response response = new Response();
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (isValidUser(userId)) {
            Country location = tradoxService.getUserLocation(userId);
            if (location == null) {
                response.setError("userLocationNotFound");
                response.setObject(false);
                return response;
            }
            Country destination = tradoxService.getCountryById(json.get("countryId"));
            if (destination == null) {
                response.setError("destinationNotFound");
                response.setObject(false);
                return response;
            }
            if (location.getShortName().equals(destination.getShortName())) {
                response.setError("locationEqualsDestination");
                response.setObject(false);
                return response;
            }
            FullRoute fullRoute = new FullRouteImpl(location, destination);
            response = tradoxService.saveRoute(userId, fullRoute);
        } else {
            response.setError("notAuthorized");
            response.setObject(false);
        }
        return response;
    }

    private boolean isValidUser(Integer userId) {
        if (userId != null) {
            return userId >= 0;
        }
        return false;
    }

}