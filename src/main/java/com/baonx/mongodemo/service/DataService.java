package com.baonx.mongodemo.service;

import com.baonx.mongodemo.model.Customer;
import com.baonx.mongodemo.model.Transaction;
import com.baonx.mongodemo.repository.CustomerRepository;
import com.baonx.mongodemo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DataService {
    private final CustomerRepository customerRepo;
    private final TransactionRepository txRepo;

    public DataService(CustomerRepository customerRepo, TransactionRepository txRepo) {
        this.customerRepo = customerRepo;
        this.txRepo = txRepo;
    }

    public Map<String, Object> seedCustomers(int count) {
        List<Customer> batch = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = randomName();
            String email = name.toLowerCase().replace(" ", ".") + i + "@example.com";
            batch.add(new Customer(name, email));
        }
        List<Customer> saved = customerRepo.saveAll(batch);
        Map<String, Object> result = new HashMap<>();
        result.put("inserted", saved.size());
        result.put("totalCustomers", customerRepo.count());
        return result;
    }

    public Map<String, Object> seedTransactions(int count) {
        long totalCustomers = customerRepo.count();
        if (totalCustomers == 0) {
            return Map.of("error", "No customers found. Please seed customers first.");
        }
        List<String> ids = customerRepo.findAll().stream().map(Customer::getId).toList();
        List<Transaction> batch = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String customerId = ids.get(ThreadLocalRandom.current().nextInt(ids.size()));
            BigDecimal amount = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(5.0, 500.0))
                    .setScale(2, RoundingMode.HALF_UP);
            Instant createdAt = Instant.now().minusSeconds(ThreadLocalRandom.current().nextInt(0, 60*60*24*30));
            String desc = randomDescription();
            batch.add(new Transaction(customerId, amount, createdAt, desc));
        }
        List<Transaction> saved = txRepo.saveAll(batch);
        Map<String, Object> result = new HashMap<>();
        result.put("inserted", saved.size());
        result.put("totalTransactions", txRepo.count());
        return result;
    }

    private String randomName() {
        String[] first = {"Anh", "Bảo", "Chi", "Dũng", "Hà", "Hải", "Huy", "Lan", "Linh", "Minh", "Nam", "Nga", "Ngọc", "Phúc", "Phương", "Quân", "Quỳnh", "Sơn", "Thảo", "Trang"};
        String[] last = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Phan", "Vũ", "Đặng", "Bùi", "Đỗ"};
        Random r = ThreadLocalRandom.current();
        return last[r.nextInt(last.length)] + " " + first[r.nextInt(first.length)];
    }

    private String randomDescription() {
        String[] ds = {"Payment", "Refund", "Order", "Top-up", "Transfer", "Subscription"};
        return ds[ThreadLocalRandom.current().nextInt(ds.length)];
    }
}
