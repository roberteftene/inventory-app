package com.roberteftene.dc.invetory_service.dto;


import com.roberteftene.dc.invetory_service.domain.enums.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductDto(Long id,
                         @NotBlank(message = "Name must not be blank") String name,
                         @NotBlank(message = "SKU must not be blank") String sku,
                         @NotBlank(message = "Description must not be blank") String description,
                         @NotNull(message = "Price is required") @Positive(message = "Price must be positive") Double price,
                         ProductCategory category) {
}
