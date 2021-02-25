package com.nc.tradox.utilities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeApi {

    public List<String> currentExchange(String departureCountryCurrency, String destinationCountryCurrency) throws InterruptedException, IOException {
        OkHttpClient client = new OkHttpClient();

        Request request1 = new Request.Builder()
                .url("https://currency-exchange.p.rapidapi.com/exchange?from=USD&to="+departureCountryCurrency+"&q=1.0")
                .get()
                .addHeader("x-rapidapi-key", "8e87b74185msh25e23189ecbb681p1a0405jsn9d4fb9fc1529")
                .addHeader("x-rapidapi-host", "currency-exchange.p.rapidapi.com")
                .build();

        Response response1 = client.newCall(request1).execute();

        Request request2 = new Request.Builder()
                .url("https://currency-exchange.p.rapidapi.com/exchange?from=" + destinationCountryCurrency + "&to="+departureCountryCurrency+"&q=1.0")
                .get()
                .addHeader("x-rapidapi-key", "8e87b74185msh25e23189ecbb681p1a0405jsn9d4fb9fc1529")
                .addHeader("x-rapidapi-host", "currency-exchange.p.rapidapi.com")
                .build();

        Response response2 = client.newCall(request2).execute();

        List<String> exchangeList = new ArrayList<>();
        exchangeList.add(response1.body().string());
        exchangeList.add(response2.body().string());

        return exchangeList;
    }

}