package com.nc.tradox;

import com.nc.tradox.api.VerificationController;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.service.TradoxService;

public class Testing {
    public static void main(String[] args) {
        VerificationController controller = new VerificationController(new TradoxService(new TradoxDataAccessService()));
        //boolean res = controller.sendMail("a.kuperman1999@gmail.com","lol");
        //System.out.println(res);
    }
}
