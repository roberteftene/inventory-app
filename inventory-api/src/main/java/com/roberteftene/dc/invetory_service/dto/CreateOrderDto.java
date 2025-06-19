package com.roberteftene.dc.invetory_service.dto;

import com.roberteftene.dc.invetory_service.domain.models.Address;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDto(@NotNull(message = "Billing address is required") Address billingAddress,
                             @NotNull(message = "Shipping address is required") Address shippingAddress,
                             @NotEmpty(message = "Order must have at least one order item") List<CreateOrderItemDto> orderItems) {
}
