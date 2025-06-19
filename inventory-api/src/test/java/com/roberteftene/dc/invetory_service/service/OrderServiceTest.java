package com.roberteftene.dc.invetory_service.service;

import com.roberteftene.dc.invetory_service.domain.enums.OrderStatus;
import com.roberteftene.dc.invetory_service.domain.models.*;
import com.roberteftene.dc.invetory_service.domain.repository.DistributionCenterRepository;
import com.roberteftene.dc.invetory_service.domain.repository.InventoryItemRepository;
import com.roberteftene.dc.invetory_service.domain.repository.OrderRepository;
import com.roberteftene.dc.invetory_service.domain.repository.ProductRepository;
import com.roberteftene.dc.invetory_service.dto.CreateOrderDto;
import com.roberteftene.dc.invetory_service.dto.OrderCreationResponse;
import com.roberteftene.dc.invetory_service.dto.CreateOrderItemDto;
import com.roberteftene.dc.invetory_service.exceptions.InsufficientStockException;
import com.roberteftene.dc.invetory_service.kafka.KafkaEventPublisher;
import com.roberteftene.dc.invetory_service.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private InventoryItemRepository inventoryRepo;

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private DistributionCenterRepository dcRepo;

    @Mock
    private ProductRepository prodRepo;

    @Mock
    private OrderMapper mapper;

    @Mock
    private KafkaEventPublisher kafkaEventPublisher;

    @InjectMocks
    private OrderService service;

    @Test
    void createOrder() {
        // Arrange
        var orderDto = buildValidCreateOrderDto();
        var orderItem = buildOrderItem(2, 1L, 1L);
        var savedOrder = buildOrder(List.of(orderItem));
        savedOrder.setId(1L);

        var orderCreationResponse = new OrderCreationResponse(savedOrder.getId(), savedOrder.getStatus());

        when(inventoryRepo.getInventoryQuantityFor(1L, 1L)).thenReturn(5);
        when(dcRepo.getReferenceById(1L)).thenReturn(orderItem.getDistributionCenter());
        when(prodRepo.getReferenceById(1L)).thenReturn(orderItem.getProduct());
        when(orderRepo.save(any(Order.class))).thenReturn(savedOrder);
        when(mapper.toOrderCreationResponse(savedOrder)).thenReturn(orderCreationResponse);

        // Act
        var result = service.createOrder(orderDto);

        // Assert
        assertEquals(1L, result.id());
    }

    @Test
    void createOrder_shouldThrowWhenInsufficientStock() {
        var orderDto = buildValidCreateOrderDto();

        when(inventoryRepo.getInventoryQuantityFor(1L, 1L)).thenReturn(1); // Less than requested

        assertThrows(InsufficientStockException.class, () -> service.createOrder(orderDto));
    }

    private CreateOrderDto buildValidCreateOrderDto() {
        return new CreateOrderDto(
                new Address("Street", "City", "Zip"),
                new Address("Street", "City", "Zip"),
                List.of(new CreateOrderItemDto(2, 1L, 1L)) // Quantity > available
        );
    }

    private Order buildOrder(List<OrderItem> items) {
        return Order.builder()
                .billingAddress(new Address("Street", "City", "Zip"))
                .shippingAddress(new Address("Street", "City", "Zip"))
                .orderItems(items)
                .status(OrderStatus.PENDING)
                .build();
    }

    private OrderItem buildOrderItem(int quantity, Long dcId, Long productId) {
        var dc = new DistributionCenter(); dc.setId(dcId);
        var product = new Product(); product.setId(productId);
        return OrderItem.builder()
                .distributionCenter(dc)
                .product(product)
                .quantityRequested(quantity)
                .build();
    }
}