package com.roberteftene.dc.invetory_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DltConsumer {

    @KafkaListener(topics = "order-placed.dlt", groupId = "dlt-group")
    public void handleDlt(Long orderId) {
        log.error("DLT message received for Order: {}", orderId);
    }
}
