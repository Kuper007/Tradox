package com.nc.tradox.api;

import com.nc.tradox.model.User;
import com.nc.tradox.service.TradoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final TradoxService tradoxService;

    @Autowired
    public AuthController(TradoxService tradoxService) {
        this.tradoxService = tradoxService;
    }

    @PostMapping("/check")
    public String auth( @RequestBody Credentials credentials, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()) {
            User user = tradoxService.auth(credentials.getEmail(),credentials.getPassword());
            if (user != null) {
                redirectAttributes.addFlashAttribute(user);
                return "redirect:/result";
            }
        }
        return "indexPage";
    }

    @GetMapping("/result")
    public String getAuthResult(HttpServletRequest request) {
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (map != null) {
            System.out.println("It is redirect!");
        } else {
            System.out.println("It is update!");
        }
        return "map";
    }
}
