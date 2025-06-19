package com.roberteftene.dc.invetory_service.service;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;
import com.roberteftene.dc.invetory_service.domain.models.InventoryItem;
import com.roberteftene.dc.invetory_service.domain.models.Order;
import com.roberteftene.dc.invetory_service.domain.models.OrderItem;
import com.roberteftene.dc.invetory_service.domain.repository.*;
import com.roberteftene.dc.invetory_service.domain.repository.projection.OrderFlatView;
import com.roberteftene.dc.invetory_service.dto.*;
import com.roberteftene.dc.invetory_service.exceptions.InsufficientStockException;
import com.roberteftene.dc.invetory_service.kafka.KafkaEventPublisher;
import com.roberteftene.dc.invetory_service.mapper.OrderMapper;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DistributionCenterRepository distributionCenterRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final OrderMapper orderMapper;
    private final KafkaEventPublisher kafkaEventPublisher;

    @RateLimiter(name = "createOrderRateLimiter", fallbackMethod = "createOrderFallback")
    @Transactional
    public OrderCreationResponse createOrder(CreateOrderDto createOrderDto) {
        boolean insufficientStock = createOrderDto.orderItems().stream()
                .anyMatch((item) -> !this.requestedQuantityAvailable(item));

        if (insufficientStock) {
            throw new InsufficientStockException("Insufficient stock for one or more items");
        }

        List<OrderItem> orderItems = createOrderDto.orderItems().stream()
                .map(this::buildOrderItemEntity)
                .toList();

        Order order = this.buildNewOrder(createOrderDto, orderItems);

        orderItems.forEach(item -> item.setOrder(order));

        Order newOrder = this.orderRepository.save(order);

        this.kafkaEventPublisher.publishOrderPlaced(newOrder.getId());

        return this.orderMapper.toOrderCreationResponse(newOrder);
    }

    @Transactional
    public void updateStockAfterOrder(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        Order order = orderOptional.orElseThrow(EntityNotFoundException::new);

        for (OrderItem orderItem : order.getOrderItems()) {
            Long dcId = orderItem.getDistributionCenter().getId();
            Long productId = orderItem.getProduct().getId();

            log.info("Updating stock for Order {} - Product {} in DC {}", orderId, productId, dcId);

            InventoryItem item = inventoryItemRepository
                    .findByDistributionCenterIdAndProductId(dcId, productId)
                    .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

            item.setQuantity(item.getQuantity() - orderItem.getQuantityRequested());
        }

        order.setStatus(OrderStatus.PROCESSING);

        this.kafkaEventPublisher.publishOrderSuccess(orderId);
    }

    @Transactional
    public void markOrderFailed(Long orderId) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        order.setStatus(OrderStatus.FAILED);
        this.kafkaEventPublisher.publishOrderFailed(orderId);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus orderStatus) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        order.setStatus(orderStatus);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrders() {
        List<OrderFlatView> ordersViews = this.orderRepository.findOrdersViews();

        Map<Long, OrderDto> grouped = new LinkedHashMap<>();

        for (OrderFlatView row : ordersViews) {
            OrderDto order = grouped.computeIfAbsent(row.getOrderId(),
                    id -> OrderDto.builder()
                            .id(id)
                            .orderStatus(row.getStatus())
                            .requestedAt(row.getRequestedAt())
                            .orderItems(new ArrayList<>())
                            .build());

            OrderItemDto orderItemDto = OrderItemDto.builder()
                    .id(row.getOrderItemId())
                    .productId(row.getProductId())
                    .quantityRequested(row.getQuantityRequested())
                    .productName(row.getProductName())
                    .build();

            order.orderItems().add(orderItemDto);
        }

        return new ArrayList<>(grouped.values());
    }


    public OrderCreationResponse createOrderFallback(CreateOrderDto dto, RequestNotPermitted ex) {
        throw new RuntimeException("Rate limit exceeded. Please try again later.");
    }

    private boolean requestedQuantityAvailable(CreateOrderItemDto item) {
        int inventoryQuantity = this.inventoryItemRepository.getInventoryQuantityFor(item.dcId(), item.productId());
        return inventoryQuantity > item.quantityRequested();
    }

    private OrderItem buildOrderItemEntity(CreateOrderItemDto createOrderItemDto) {
        return OrderItem.builder()
                .distributionCenter(distributionCenterRepository.getReferenceById(createOrderItemDto.dcId()))
                .product(productRepository.getReferenceById(createOrderItemDto.productId()))
                .quantityRequested(createOrderItemDto.quantityRequested())
                .build();
    }

    private Order buildNewOrder(CreateOrderDto createOrderDto, List<OrderItem> orderItems) {
        return Order.builder()
                .billingAddress(createOrderDto.billingAddress())
                .shippingAddress(createOrderDto.shippingAddress())
                .orderItems(orderItems)
                .status(OrderStatus.PENDING)
                .build();
    }

}
