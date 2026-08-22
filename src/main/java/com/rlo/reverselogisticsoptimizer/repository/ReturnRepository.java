package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.Return;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReturnRepository extends MongoRepository<Return, String> {
}