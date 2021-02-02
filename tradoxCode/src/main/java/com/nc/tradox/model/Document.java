package com.nc.tradox.model;

public interface Document {
    Integer getDocumentId();

    void setDocumentId(Integer documentId);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getFileLink();

    void setFileLink(String fileLink);
}