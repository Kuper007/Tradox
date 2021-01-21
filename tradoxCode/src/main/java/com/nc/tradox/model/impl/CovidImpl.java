package com.nc.tradox.model.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CovidImpl implements com.nc.tradox.model.Covid {

    protected int summaryTotalCases;
    protected int todayTotalCases;
    protected int summaryActiveCases;
    protected int todayActiveCases;
    protected int todayDeaths;
    protected int summaryRecovered;
    protected int todayRecovered;

    public CovidImpl(){}

    public CovidImpl(int summaryTotalCases,
                     int todayTotalCases,
                     int summaryActiveCases,
                     int todayActiveCases,
                     int todayDeaths,
                     int summaryRecovered,
                     int todayRecovered) {
        this.summaryTotalCases = summaryTotalCases;
        this.todayTotalCases = todayTotalCases;
        this.summaryActiveCases = summaryActiveCases;
        this.todayActiveCases = todayActiveCases;
        this.todayDeaths = todayDeaths;
        this.summaryRecovered = summaryRecovered;
        this.todayRecovered = todayRecovered;
    }

    public CovidImpl(ResultSet res){
        try {
            this.summaryTotalCases = res.getInt("summary_total_cases");
            this.todayTotalCases = res.getInt("today_total_cases");
            this.summaryActiveCases = res.getInt("summary_active_cases");
            this.todayActiveCases = res.getInt("today_active_cases");
            this.todayDeaths = res.getInt("today_deaths");
            this.summaryRecovered = res.getInt("summary_recovered");
            this.todayRecovered = res.getInt("today_recovered");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getSummaryTotalCases() {
        return this.summaryTotalCases;
    }

    @Override
    public void setSummaryTotalCases(int summaryTotalCases) {
        this.summaryTotalCases = summaryTotalCases;
    }

    @Override
    public int getTodayTotalCases() {
        return this.todayTotalCases;
    }

    @Override
    public void setTodayTotalCases(int todayTotalCases) {
        this.todayTotalCases = todayTotalCases;
    }

    @Override
    public int getSummaryActiveCases() {
        return this.summaryActiveCases;
    }

    @Override
    public void setSummaryActiveCases(int summaryActiveCases) {
        this.summaryActiveCases = summaryActiveCases;
    }

    @Override
    public int getTodayActiveCases() {
        return this.todayActiveCases;
    }

    @Override
    public void setTodayActiveCases(int todayActiveCases) {
        this.todayActiveCases = todayActiveCases;
    }

    @Override
    public int getTodayDeaths() {
        return this.todayDeaths;
    }

    @Override
    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    @Override
    public int getSummaryRecovered() {
        return this.summaryRecovered;
    }

    @Override
    public void setSummaryRecovered(int summaryRecovered) {
        this.summaryRecovered = summaryRecovered;
    }

    @Override
    public int getTodayRecovered() {
        return this.todayRecovered;
    }

    @Override
    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    @Override
    public String toString() {
        return "CovidImpl{" +
                "summaryTotalCases=" + summaryTotalCases +
                ", todayTotalCases=" + todayTotalCases +
                ", summaryActiveCases=" + summaryActiveCases +
                ", todayActiveCases=" + todayActiveCases +
                ", todayDeaths=" + todayDeaths +
                ", summaryRecovered=" + summaryRecovered +
                ", todayRecovered=" + todayRecovered +
                '}';
    }
}
