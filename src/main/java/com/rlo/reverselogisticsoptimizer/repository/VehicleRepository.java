package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
}