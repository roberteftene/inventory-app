package com.roberteftene.dc.invetory_service.dto;

import lombok.Builder;

@Builder
public record OrderItemDto(
        Long id,
        Long productId,
        Integer quantityRequested,
        String productName
) {
}
