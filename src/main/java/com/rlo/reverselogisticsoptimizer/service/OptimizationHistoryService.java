package com.rlo.reverselogisticsoptimizer.service;

import com.rlo.reverselogisticsoptimizer.entity.OptimizationHistory;
import com.rlo.reverselogisticsoptimizer.repository.OptimizationHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OptimizationHistoryService {

    private final OptimizationHistoryRepository repository;

    public OptimizationHistoryService(
            OptimizationHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public OptimizationHistory save(
            OptimizationHistory history
    ) {
        history.setOptimizedAt(LocalDateTime.now());

        return repository.save(history);
    }

    public List<OptimizationHistory> getAll() {
        return repository.findAll();
    }

    public List<OptimizationHistory> getByReturnId(
            String returnId
    ) {
        return repository.findByReturnId(returnId);
    }
}