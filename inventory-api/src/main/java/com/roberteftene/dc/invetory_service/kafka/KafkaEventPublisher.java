package com.roberteftene.dc.invetory_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    @Value("${kafka.topic.orderPlaced}")
    private String orderPlacedTopic;

    @Value("${kafka.topic.orderFailed}")
    private String orderFailedTopic;

    @Value("${kafka.topic.orderSuccess}")
    private String orderSuccessTopic;

    public void publishOrderPlaced(Long orderId) {
        kafkaTemplate.send(orderPlacedTopic, orderId);
    }

    public void publishOrderSuccess(Long orderId) {
        this.kafkaTemplate.send(orderSuccessTopic, orderId);
    }

    public void publishOrderFailed(Long orderId) {
        kafkaTemplate.send(orderFailedTopic, orderId);
    }

}
