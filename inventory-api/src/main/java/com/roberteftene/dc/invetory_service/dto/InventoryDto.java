package com.roberteftene.dc.invetory_service.dto;

import java.time.LocalDate;

public record InventoryDto(Long id, Integer quantity, LocalDate expiresAt, LocalDate updatedAt) {}
