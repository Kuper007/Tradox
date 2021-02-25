package com.nc.tradox;

import com.nc.tradox.api.EmailController;

public class Testing {
    public static void main(String[] args) {
        EmailController e = new EmailController();
        e.sendMail("kuperman.anton@stud.onu.edu.ua","test","code");
    }
}
