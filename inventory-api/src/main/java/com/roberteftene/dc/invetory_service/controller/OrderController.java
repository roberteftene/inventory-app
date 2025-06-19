package com.roberteftene.dc.invetory_service.controller;

import com.roberteftene.dc.invetory_service.dto.CreateOrderDto;
import com.roberteftene.dc.invetory_service.dto.OrderCreationResponse;
import com.roberteftene.dc.invetory_service.dto.OrderDto;
import com.roberteftene.dc.invetory_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with its order items",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrderDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request body"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized access")
            }
    )
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public OrderCreationResponse createOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        return this.orderService.createOrder(createOrderDto);
    }

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description = "Returns all orders with their associated order items",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of orders",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(responseCode = "403", description = "Unauthorized or insufficient permissions")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getOrders() {
        return this.orderService.getOrders();
    }
}
