package com.nc.tradox.utilities;

public class DocumentTest {

    public static void main(String[] args) throws Exception{
        DocumentApi documentApi = new DocumentApi();

        System.out.println(documentApi.documentList().toString());
    }

}
