package com.nc.tradox.utilities;

public class ExchangeTest {

    public static void main(String[] args) throws Exception{
        ExchangeApi exchangeApi = new ExchangeApi();
        System.out.println(exchangeApi.currentExchange("UAH", "RUB").toString());
    }

}
