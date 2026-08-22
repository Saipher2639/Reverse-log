package com.rlo.reverselogisticsoptimizer.service;

import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import com.rlo.reverselogisticsoptimizer.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        if (vehicle.getCapacity() <= 0) {
            throw new IllegalArgumentException(
                    "Vehicle capacity must be greater than zero"
            );
        }

        if (vehicle.getCostPerKm() <= 0) {
            throw new IllegalArgumentException(
                    "Cost per kilometer must be greater than zero"
            );
        }

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}