package com.rlo.reverselogisticsoptimizer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "routes")
@Getter
@Setter
@NoArgsConstructor
public class Route {

    @Id
    private String id;

    private String returnId;

    private String facilityId;

    private String vehicleId;

    private double distance;

    private double transportCost;

    private double processingCost;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double totalCost;
}