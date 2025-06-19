package com.roberteftene.dc.invetory_service.service;

import com.roberteftene.dc.invetory_service.domain.repository.InventoryItemRepository;
import com.roberteftene.dc.invetory_service.dto.InventoryDto;
import com.roberteftene.dc.invetory_service.mapper.InventoryItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemMapper inventoryItemMapper;

    public InventoryService(InventoryItemRepository inventoryItemRepository, InventoryItemMapper inventoryItemMapper) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemMapper = inventoryItemMapper;
    }

    public List<InventoryDto> getInventory(Long dcId, String sku) {
        return this.inventoryItemRepository
                .findByDistributionCenterIdAndProductSku(dcId, sku)
                .stream()
                .map(inventoryItemMapper::toDto)
                .toList();
    }
}
