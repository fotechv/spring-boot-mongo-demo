package com.baonx.mongodemo.controller;

import com.baonx.mongodemo.service.DataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final DataService dataService;

    public ApiController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/customers/seed")
    public ResponseEntity<Map<String, Object>> seedCustomers(@RequestParam(defaultValue = "50") int count) {
        return ResponseEntity.ok(dataService.seedCustomers(count));
    }

    @PostMapping("/transactions/seed")
    public ResponseEntity<Map<String, Object>> seedTransactions(@RequestParam(defaultValue = "100") int count) {
        Map<String, Object> result = dataService.seedTransactions(count);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        return ResponseEntity.ok(result);
    }
}
