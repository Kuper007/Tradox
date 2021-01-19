package com.nc.tradox.api;

import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/account")
@RestController
public class AccountController {

    private final TradoxService tradoxService;

    @Autowired
    public AccountController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    public void saveInfo() {

    }

    public void deleteRoute() {

    }

}
