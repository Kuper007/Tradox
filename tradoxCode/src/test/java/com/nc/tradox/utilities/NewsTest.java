package com.nc.tradox.utilities;
public class NewsTest {

    public static void main(String[] args) throws Exception{
        NewsApi newsApi = new NewsApi();
        System.out.println(newsApi.news().toString());
    }

}
