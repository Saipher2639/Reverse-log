package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository extends MongoRepository<Route, String> {
}