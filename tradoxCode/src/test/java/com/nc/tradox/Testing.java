package com.nc.tradox;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Status;
import com.nc.tradox.utilities.ExchangeApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Testing {
    public static void main(String[] args) {
        ExchangeApi exchangeApi = new ExchangeApi();
        try {
            List<String> ex = exchangeApi.currentExchange("Ukraine","Germany");
            for (String s: ex){
                System.out.println(s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
