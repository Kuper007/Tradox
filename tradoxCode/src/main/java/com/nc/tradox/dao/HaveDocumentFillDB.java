package com.nc.tradox.dao;

import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.utilities.DocumentApi;
import com.nc.tradox.utilities.HaveDocument;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HaveDocumentFillDB {

    private static final Logger log = Logger.getLogger(HaveDocumentFillDB.class.getName());
    TradoxDataAccessService tradoxDataAccessService = new TradoxDataAccessService();
    DocumentApi documentApi = new DocumentApi();

    public void haveDocumentFillDB() {
        List<HaveDocument> haveDocumentList = documentApi.documentList();
        if (haveDocumentList != null){
            try {
                int count = 0;
                Statement statement = tradoxDataAccessService.connection.createStatement();
                for (HaveDocument haveDocument : haveDocumentList) {
                    if (haveDocument == null ||
                            haveDocument.getDestination() == null ||
                            haveDocument.getDeparture() == null ||
                            haveDocument.getDestination().getShortName() == null ||
                            haveDocument.getDeparture().getShortName() == null) continue;
                    String haveDocumentQuery = "INSERT INTO HAVE_DOCUMENT(DOCUMENT_ID, DESTINATION_ID, DEPARTURE_ID) " +
                            "VALUES (" + haveDocument.getDocumentId() + ", '" +
                            haveDocument.getDestination().getShortName() + "', '" +
                            haveDocument.getDeparture().getShortName() + "')";
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
                log.log(Level.SEVERE, "SQL error", exception);
            }
        }
    }
}
