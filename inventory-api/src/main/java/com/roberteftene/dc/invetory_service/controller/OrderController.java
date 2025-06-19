package com.roberteftene.dc.invetory_service.controller;

import com.roberteftene.dc.invetory_service.dto.CreateOrderDto;
import com.roberteftene.dc.invetory_service.dto.OrderCreationResponse;
import com.roberteftene.dc.invetory_service.dto.OrderDto;
import com.roberteftene.dc.invetory_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: add swagger doc
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public OrderCreationResponse createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) throws InterruptedException {
        return this.orderService.createOrder(createOrderDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<OrderDto> getOrders() {
        return this.orderService.getOrders();
    }
}
