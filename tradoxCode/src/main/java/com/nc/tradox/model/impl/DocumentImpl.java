package com.nc.tradox.model.impl;

import com.nc.tradox.model.Document;

public class DocumentImpl implements Document {

    protected Integer documentId;
    protected String name;
    protected String description;
    protected String fileLink;

    public DocumentImpl() {

    }

    public DocumentImpl(Integer documentId, String name, String description, String fileLink) {
        this.documentId = documentId;
        this.name = name;
        this.description = description;
        this.fileLink = fileLink;
    }

    @Override
    public Integer getDocumentId() {
        return documentId;
    }

    @Override
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getFileLink() {
        return fileLink;
    }

    @Override
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

}