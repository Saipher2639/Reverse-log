package com.rlo.reverselogisticsoptimizer.entity;

import com.rlo.reverselogisticsoptimizer.enums.ProductCondition;
import com.rlo.reverselogisticsoptimizer.enums.ReturnStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "returns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Return {

    @Id
    private String id;

    private double latitude;

    private double longitude;

    @Valid
    @NotNull(message = "Product is required")
    private Product product;

    private String customerLocation;

    @NotNull(message = "Product condition is required")
    private ProductCondition condition;

    private ReturnStatus status;

    private LocalDateTime createdAt;
}