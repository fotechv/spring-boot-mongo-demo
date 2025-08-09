package com.baonx.mongodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String customerPage() {
        return "customer"; // templates/customer.html
    }

    @GetMapping("/transaction")
    public String transactionPage() {
        return "transaction"; // templates/transaction.html
    }
}
