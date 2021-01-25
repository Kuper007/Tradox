package com.nc.tradox.api;

import com.nc.tradox.model.InfoData;
import com.nc.tradox.model.Route;
import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
        Route route = tradoxService.getRoute((String) session.getAttribute("userId"),destinationId);
        session.setAttribute("currentRoute",route);
        return route;
    }

    @GetMapping("/save")
    public Boolean saveRoute(HttpSession session) {
        Route r  = (Route) session.getAttribute("currentRoute");
        int userId = (int) session.getAttribute("userId");
        return tradoxService.saveRoute(r,userId);
    }

    @PostMapping("/editTransits")
    public void editTransits(@RequestBody Set<InfoData> transits, HttpSession session) {
        Integer routeId = ((Route) session.getAttribute("currentRoute")).getElementId();
        tradoxService.editTransits((int) session.getAttribute("userId"),routeId,transits);
    }


}