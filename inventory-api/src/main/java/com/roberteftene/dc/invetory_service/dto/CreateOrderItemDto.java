package com.roberteftene.dc.invetory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDto(@Min(value = 1, message = "Quantity must be at least 1") Integer quantityRequested,
                                 @NotNull(message = "Product ID is required") Long productId,
                                 @NotNull(message = "Distribution Center ID is required") Long dcId) {
}
