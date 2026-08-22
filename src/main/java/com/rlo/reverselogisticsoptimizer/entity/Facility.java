package com.rlo.reverselogisticsoptimizer.entity;

import com.rlo.reverselogisticsoptimizer.enums.FacilityType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {

    @Id
    private String id;

    private double latitude;

    private double longitude;

    private String name;

    private String location;

    private FacilityType type;

    @Positive(message = "Facility capacity must be greater than 0")
    private double capacity;

    @PositiveOrZero(message = "Current load cannot be negative")
    private double currentLoad;
}