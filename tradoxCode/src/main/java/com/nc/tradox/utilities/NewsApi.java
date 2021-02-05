package com.nc.tradox.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.Country;
import com.nc.tradox.model.NewsItem;
import com.nc.tradox.model.impl.NewsItemImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsApi {

    TradoxDataAccessService tradoxDataAccessService;

    public List<NewsItem> news(String destinationCountry) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://newsapi.org/v2/top-headlines?" + "country=" + destinationCountry + "&" +
                        "apiKey=2d513938129848ec9f3deab67e1a6422")
                .get()
                .addHeader("x-rapidapi-key", "8e87b74185msh25e23189ecbb681p1a0405jsn9d4fb9fc1529")
                .addHeader("x-rapidapi-host", "currency-exchange.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String newsJson = response.body().string();

        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(newsJson, Root.class);

        List<NewsItem> newsList = new ArrayList<>();
        for (Article article : root.articles) {

            Country destination = tradoxDataAccessService.getCountryById(destinationCountry);

            NewsItem newsItem = new NewsItemImpl(null, article.content, java.util.Calendar.getInstance().getTime(), destination);
            newsList.add(newsItem);
        }
        return newsList;
    }

    public static class Source {
        public String id;
        public String name;
    }

    public static class Article {
        public Source source;
        public String author;
        public String title;
        public String description;
        public String url;
        public String urlToImage;
        public Date publishedAt;
        public String content;

    }

    public static class Root {
        public String status;
        public int totalResults;
        public List<Article> articles;

    }

}
