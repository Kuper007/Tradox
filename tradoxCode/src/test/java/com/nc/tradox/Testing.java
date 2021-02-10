package com.nc.tradox;

import com.nc.tradox.api.VerificationController;
import com.nc.tradox.dao.impl.TradoxDataAccessService;
import com.nc.tradox.model.User;
import com.nc.tradox.model.impl.UserImpl;
import com.nc.tradox.service.TradoxService;

public class Testing {
    public static void main(String[] args) {
        User user = new UserImpl();
        user.setUserType(User.UserTypeEnum.valueOf("admin"));
        System.out.println(user.getUserType());
    }
}
