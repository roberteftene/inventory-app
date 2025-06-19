package com.roberteftene.dc.invetory_service.dto;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;
import lombok.Builder;

@Builder
public record OrderCreationResponse(Long id, OrderStatus status) {
}
