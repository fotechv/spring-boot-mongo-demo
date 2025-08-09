package com.baonx.mongodemo.controller;

import com.baonx.mongodemo.model.Customer;
import com.baonx.mongodemo.model.Transaction;
import com.baonx.mongodemo.repository.CustomerRepository;
import com.baonx.mongodemo.repository.TransactionRepository;
import com.baonx.mongodemo.service.DataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final DataService dataService;
    private final CustomerRepository customerRepo;
    private final TransactionRepository txRepo;

    public ApiController(DataService dataService, CustomerRepository customerRepo, TransactionRepository txRepo) {
        this.dataService = dataService;
        this.customerRepo = customerRepo;
        this.txRepo = txRepo;
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

    // --- NEW: list customers (paging) ---
    @GetMapping("/customers")
    public Page<Customer> listCustomers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return customerRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
    }

    // --- NEW: list transactions (paging, sort by createdAt desc) ---
    @GetMapping("/transactions")
    public Page<Transaction> listTransactions(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return txRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
