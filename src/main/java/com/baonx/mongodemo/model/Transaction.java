package com.baonx.mongodemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String customerId; // tham chiếu tới Customer.id
    private BigDecimal amount;
    private Instant createdAt;
    private String description;

    public Transaction() {}

    public Transaction(String customerId, BigDecimal amount, Instant createdAt, String description) {
        this.customerId = customerId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
