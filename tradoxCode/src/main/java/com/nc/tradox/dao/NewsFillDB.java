package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.NewsItem;
import com.nc.tradox.utilities.NewsApi;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewsFillDB {

    private static final Logger log = Logger.getLogger(NewsFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    NewsApi newsApi = new NewsApi();

    public void newsFillDB(){
        List<NewsItem> newsItemList = null;
        try {
            newsItemList = newsApi.news();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newsItemList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (NewsItem newsItem: newsItemList) {
                    String haveDocumentQuery = "INSERT INTO NEWS(TEXT, \"DATE\", COUNTRY_ID) " +
                            "VALUES ('" + newsItem.getText().replace("'", " ") + "', " +
                            "TO_DATE(sysdate, 'YYYY-MM-DD HH24:MI:SS')" + ", '" +
                            newsItem.getCountry().getShortName() + "')";
                    System.out.println(haveDocumentQuery);
                    statement.executeUpdate(haveDocumentQuery);
                    count++;
                    if (count == 200){
                        count = 0;
                        statement.close();
                        statement = tradoxDataAccessService.connection.createStatement();
                    }
                }
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
