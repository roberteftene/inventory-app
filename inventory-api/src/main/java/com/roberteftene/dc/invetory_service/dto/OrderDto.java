package com.roberteftene.dc.invetory_service.dto;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDto(Long id, LocalDateTime requestedAt, OrderStatus orderStatus, List<OrderItemDto> orderItems) {}
