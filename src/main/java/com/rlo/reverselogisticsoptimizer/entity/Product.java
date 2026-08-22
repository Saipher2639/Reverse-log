package com.rlo.reverselogisticsoptimizer.entity;

import lombok.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String category;

    @Positive(message = "Product weight must be greater than 0")
    private double weight;

    @Positive(message = "Product value must be greater than 0")
    private double value;
}