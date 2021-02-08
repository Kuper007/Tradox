package com.nc.tradox.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.model.Country;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v1/admin")
@RestController
public class AdminController {

    private final TradoxService tradoxService;

    @Autowired
    public AdminController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @GetMapping("/get")
    public String getInfo(){
        String json = "";
        List<Country> countries = tradoxService.getAllCountries();
        if (!countries.isEmpty()) {
            Map<Integer,Country> map = new HashMap<>();
            for (int i=0; i<countries.size();i++) {
                map.put(i,countries.get(i));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                json = objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return json;
    }
}
