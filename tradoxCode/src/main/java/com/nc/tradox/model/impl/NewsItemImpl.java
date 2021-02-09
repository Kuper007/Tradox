package com.nc.tradox.model.impl;

import com.nc.tradox.model.Country;
import com.nc.tradox.model.NewsItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NewsItemImpl implements NewsItem {

    protected Integer newsItemId;
    protected String text;
    protected Date date;
    protected Country country;

    public NewsItemImpl() {

    }

    public NewsItemImpl(Integer newsItemId,
                        String text,
                        Date date,
                        Country country) {
        this.newsItemId = newsItemId;
        this.text = text;
        this.date = date;
        this.country = country;
    }

    public NewsItemImpl(ResultSet resultSet) throws SQLException {
        this.newsItemId = resultSet.getInt("NEWS_ID");
        this.text = resultSet.getString("TEXT");
        this.date = resultSet.getDate("DATE");
    }

    @Override
    public Integer getNewsItemId() {
        return newsItemId;
    }

    @Override
    public void setNewsItemId(Integer newsItemId) {
        this.newsItemId = newsItemId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Country getCountry() {
        return country == null ? new CountryImpl() : country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

}