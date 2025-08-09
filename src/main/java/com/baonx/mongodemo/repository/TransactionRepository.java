package com.baonx.mongodemo.repository;

import com.baonx.mongodemo.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
