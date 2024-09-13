package com.example.demo.productApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @Positive(message = "Price must be positive")
    private Double price;

}
