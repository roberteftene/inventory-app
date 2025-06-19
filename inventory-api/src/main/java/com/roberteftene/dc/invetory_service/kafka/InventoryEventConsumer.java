package com.roberteftene.dc.invetory_service.kafka;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;
import com.roberteftene.dc.invetory_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "${kafka.topic.orderPlaced}", groupId = "inventory-update-group")
    public void handle(Long orderId) {
        log.info("Updating the stock after order: {}", orderId);
        try {
            this.orderService.updateStockAfterOrder(orderId);
        } catch (Exception ex) {
            log.error("Failed to update stock for order {}", orderId);
            this.orderService.markOrderFailed(orderId);
        }
        log.info("Stock updated successfully for order: {}", orderId);
    }

    @KafkaListener(topics = "${kafka.topic.orderSuccess}", groupId = "inventory-update-group")
    public void handleOrderSuccess(Long orderId) {
        this.orderService.updateStatus(orderId, OrderStatus.PROCESSING);
    }
}
