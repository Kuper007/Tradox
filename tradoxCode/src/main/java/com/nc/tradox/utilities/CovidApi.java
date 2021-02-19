package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.nc.tradox.model.Covid;
import com.nc.tradox.model.impl.CovidImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class CovidApi {

    public Covid covidInfo(String country) throws IOException, InterruptedException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://coronavirus-map.p.rapidapi.com/v1/summary/region?region=" + country)
                .get()
                .addHeader("x-rapidapi-key", "8e87b74185msh25e23189ecbb681p1a0405jsn9d4fb9fc1529")
                .addHeader("x-rapidapi-host", "coronavirus-map.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String json = Objects.requireNonNull(response.body()).string();
        ObjectMapper objectMapper = new ObjectMapper();
        Root root;
        try {
            root = objectMapper.readValue(json, Root.class);
        }catch (UnrecognizedPropertyException exception){
            return new CovidImpl(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0);
        }

        return new CovidImpl(
                root.data.summary.total_cases,
                root.data.change.total_cases,
                root.data.summary.active_cases,
                root.data.change.active_cases,
                root.data.summary.deaths,
                root.data.change.deaths,
                root.data.summary.recovered,
                root.data.change.recovered);
    }

    public static class Summary {
        public int total_cases;
        public int active_cases;
        public int deaths;
        public int recovered;
        public int critical;
        public int tested;
        public double death_ratio;
        public double recovery_ratio;
    }

    public static class Change {
        public int total_cases;
        public int active_cases;
        public int deaths;
        public int recovered;
        public int critical;
        public int tested;
        public double death_ratio;
        public double recovery_ratio;
    }

    public static class Data {
        public Summary summary;
        public Change change;
    }

    public static class Root {
        public int status;
        public String type;
        public Data data;
    }

}