package com.nc.tradox.utilities;

public class SpeedLimitTest {

    public static void main(String[] args) throws Exception{
        SpeedLimitApi speedLimitApi = new SpeedLimitApi();
        System.out.println(speedLimitApi.speedLimitList().toString());
    }

}
