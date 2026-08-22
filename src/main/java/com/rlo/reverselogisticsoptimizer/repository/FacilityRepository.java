package com.rlo.reverselogisticsoptimizer.repository;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacilityRepository extends MongoRepository<Facility, String> {
}