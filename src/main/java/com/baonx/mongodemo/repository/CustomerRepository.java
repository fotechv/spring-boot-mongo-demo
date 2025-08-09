package com.baonx.mongodemo.repository;

import com.baonx.mongodemo.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
