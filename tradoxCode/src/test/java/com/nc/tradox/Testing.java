package com.nc.tradox;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Status;
import com.nc.tradox.utilities.ExchangeApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testing {
    public static void main(String[] args) {
        TradoxDataAccessService service = new TradoxDataAccessService();
        Map<String,String> map = new HashMap<String, String>();
        map.put("first_name","Testony");
        map.put("last_name","Testerman");
        map.put("birth_date","20/03/2004");
        map.put("email","test321@gmail.com");
        map.put("password","Anton13493");
        boolean res = service.registrate(map);
        System.out.println(res);


    }
}
