package com.rlo.reverselogisticsoptimizer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "optimization_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptimizationHistory {

    @Id
    private String id;

    private String returnId;

    private String facilityId;

    private String facilityName;

    private String vehicleId;

    private String vehicleNumber;

    private double distance;

    private double transportCost;

    private double processingCost;

    private double totalCost;

    private double capacityUtilization;

    private double utilizationPenalty;

    private double finalScore;

    private LocalDateTime optimizedAt;
}