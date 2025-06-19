package com.roberteftene.dc.invetory_service.controller;

import com.roberteftene.dc.invetory_service.dto.InventoryDto;
import com.roberteftene.dc.invetory_service.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Operations related to inventory items")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(
            summary = "Get inventory items by distribution center and product SKU",
            description = "Returns all inventory entries for a given distribution center and product SKU. Can be used to check stock levels and expiration dates.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of inventory items",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InventoryDto.class)))
                    ),
                    @ApiResponse(responseCode = "400", description = "Missing or invalid parameters"),
                    @ApiResponse(responseCode = "404", description = "No inventory found for given parameters")
            }
    )
    @GetMapping
    public List<InventoryDto> getInventory(
            @Parameter(description = "ID of the distribution center", required = true)
            @RequestParam
            @Positive(message = "Distribution center ID must be positive")
            Long dcId,
            @Parameter(description = "SKU of the product", required = true)
            @RequestParam
            @NotBlank(message = "Product SKU is required")
            String sku) {
        return this.inventoryService.getInventory(dcId, sku);
    }
}
