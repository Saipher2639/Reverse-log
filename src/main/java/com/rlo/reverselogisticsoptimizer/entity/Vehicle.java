package com.rlo.reverselogisticsoptimizer.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    private String id;

    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @Positive(message = "Vehicle capacity must be greater than 0")
    private double capacity;

    private String currentLocation;

    private boolean available;

    @Positive(message = "Cost per km must be greater than 0")
    private double costPerKm;
}