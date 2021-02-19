package com.nc.tradox.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.NewsItem;
import com.nc.tradox.model.impl.NewsItemImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NewsApi {

    CountryApi countryApi = new CountryApi();

    public List<NewsItem> news() throws IOException {
        OkHttpClient client = new OkHttpClient();

        List<Country> countryList = countryApi.fillCountryName();
        List<String> newsJsonList = new ArrayList<>();
        for (Country country: countryList) {
            Request request = new Request.Builder()
                    .url("http://api.mediastack.com/v1/news?access_key=4e24ab3423c68f74bac340bec991f3bf" +
                            "&countries=" + country.getShortName().toLowerCase() + "&languages=" + "en" + "&limit=5")
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            String newsJson = response.body().string();
            newsJsonList.add(newsJson);
        }
        File file = new File("tradoxCode/src/main/resources/jsonsAndFriends/news.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("{\"MainArr\":[");
        boolean isThisFirstLine = true;
        for (String newsJson: newsJsonList){
            if (isThisFirstLine){
                fileWriter.write(newsJson + System.getProperty("line.separator"));
                isThisFirstLine = false;
            }else {
                fileWriter.write("," + newsJson + System.getProperty("line.separator"));
            }
        }
        fileWriter.write("]}");
        fileWriter.close();
        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(file, Root.class);

        List<NewsItem> newsList = new ArrayList<>();
        for (MainArr mainArr : root.mainArr) {
            for (Datum datum: mainArr.data) {
                NewsItem newsItem = new NewsItemImpl(null, datum.title, java.util.Calendar.getInstance().getTime(), null);
                newsList.add(newsItem);
            }
        }

        Iterator<NewsItem> iterator = newsList.iterator();
        for (Country country: countryList){
            if(iterator.hasNext()){
                iterator.next().setCountry(country);
            }
        }
        return newsList;
    }

    public static class Pagination{
        public int limit;
        public int offset;
        public int count;
        public int total;
    }

    public static class Datum{
        public Object author;
        public String title;
        public String description;
        public String url;
        public String source;
        public String image;
        public String category;
        public String language;
        public String country;
        public Date published_at;
    }

    public static class MainArr{
        public Pagination pagination;
        public List<Datum> data;
    }

    public static class Root{
        @JsonProperty("MainArr")
        public List<MainArr> mainArr;
    }

}
