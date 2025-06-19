package com.roberteftene.dc.invetory_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @KafkaListener(topics = "${kafka.topic.orderPlaced}", groupId = "email-group")
    public void handleReceivingOrder(String orderId) {
        log.info("Sending email for pending order: {}", orderId);
    }

    @KafkaListener(topics = "${kafka.topic.orderFailed}", groupId = "email-group")
    public void handleFailure(Long orderId) {
        log.info("Sending email for failing order: {}", orderId);
    }
}
