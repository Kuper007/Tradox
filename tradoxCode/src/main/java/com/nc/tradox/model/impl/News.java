package com.nc.tradox.model.impl;

import com.nc.tradox.model.NewsItem;

import java.util.List;

public class News extends ListOfElements<NewsItem> {

    public News(List<NewsItem> elementsList) {
        super(elementsList);
    }

}