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
    TradoxDataAccessService tradoxDataAccessService;
    DocumentApi documentApi;

    public void haveDocumentFillDB() {
        Connection connection = tradoxDataAccessService.connection;
        List<HaveDocument> haveDocumentList = documentApi.documentList();

        for (HaveDocument haveDocument : haveDocumentList) {
            try {
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(
                        "INSERT INTO HAVE_DOCUMENT(DOCUMENT_ID, DESTINATION_ID, DEPARTURE_ID)" +
                                "VALUES (" + haveDocument.getDocumentId() + ", " +
                                haveDocument.getDestination() + ", " +
                                haveDocument.getDeparture() + ")");
                statement.close();
            } catch (SQLException exception) {
                log.log(Level.SEVERE, "SQL exception", exception);
            }

        }
    }

}
