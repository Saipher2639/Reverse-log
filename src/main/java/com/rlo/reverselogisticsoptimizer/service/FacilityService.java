package com.rlo.reverselogisticsoptimizer.service;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.repository.FacilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityService {
    private double latitude;
    private double longitude;
    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public Facility createFacility(Facility facility) {
        if (facility.getCurrentLoad() > facility.getCapacity()) {
            throw new IllegalArgumentException(
                    "Current load cannot exceed facility capacity"
            );
        }

        return facilityRepository.save(facility);
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }
}