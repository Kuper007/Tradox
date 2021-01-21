package com.nc.tradox.utilities;

public class ConsulateTest {

    public static void main(String[] args) throws Exception{
        ConsulateApi consulateApi = new ConsulateApi();
        System.out.println(consulateApi.consulateList().toString());
    }

}
