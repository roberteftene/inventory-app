package com.roberteftene.dc.invetory_service.controller;

import com.roberteftene.dc.invetory_service.dto.ProductDto;
import com.roberteftene.dc.invetory_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Products", description = "Endpoints related to product management")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(
            summary = "Create a new product",
            description = "Adds a new product to the catalog and returns the created product.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid product data", content = @Content)
            }
    )
    @PostMapping
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return this.productService.createProduct(productDto);
    }

}

