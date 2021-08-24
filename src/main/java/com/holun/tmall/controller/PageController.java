package com.holun.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器（用于重定向到指定的页面）
 */
@Controller
public class PageController {

    @GetMapping("/foreToLoginPage")
    public String toLoginPage() {
        return "fore/login";
    }

    @GetMapping("/foreToRegisterPage")
    public String toRegisterPage() {
        return "fore/register";
    }

    @GetMapping("/foreToRegisterSuccessPage")
    public String toRegisterSuccessPage() {
        return "fore/registerSuccess";
    }

    @GetMapping("/foreToConfirmOrderPage")
    public String toConfirmOrderPage() {
        return "fore/confirmOrder";
    }

    @GetMapping("/foreToConfirmPaymentPage")
    public String toConfirmPaymentPage(){
        return "fore/confirmPayment";
    }

    @GetMapping("/403")
    public String noPermission(){
        return "error/403";
    }

    @GetMapping("/foreRegister/admin")
    public String toRegisterAdminPage() {
        return "fore/registerAdmin";
    }
}
