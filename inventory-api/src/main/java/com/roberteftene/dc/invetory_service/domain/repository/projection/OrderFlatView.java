package com.roberteftene.dc.invetory_service.domain.repository.projection;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;

import java.time.LocalDateTime;

public interface OrderFlatView {
    Long getOrderId();
    OrderStatus getStatus();
    LocalDateTime getRequestedAt();
    Long getOrderItemId();
    Long getProductId();
    String getProductName();
    Integer getQuantityRequested();
}