package com.github.lksay3d.GHADash.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Custom error handling
        return "error";
    }

    // @Override
    // public String getErrorPath() {
    //     return "/error";
    // }
}