package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}