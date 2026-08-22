package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.OptimizationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OptimizationHistoryRepository
        extends MongoRepository<OptimizationHistory, String> {

    List<OptimizationHistory> findByReturnId(String returnId);
}