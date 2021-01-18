package com.nc.tradox;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Status;

import java.util.Map;

public class Testing {
    public static void main(String[] args) {
        TradoxDataAccessService service = new TradoxDataAccessService();
        Map<String, Status.StatusEnum> m = service.getCountriesWhereNameLike("'UK'","Uk");
        for (String s: m.keySet()){
            System.out.println(s+':'+m.get(s));
        }

    }
}
