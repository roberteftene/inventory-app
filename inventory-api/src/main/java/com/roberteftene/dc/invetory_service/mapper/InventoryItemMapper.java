package com.roberteftene.dc.invetory_service.mapper;

import com.roberteftene.dc.invetory_service.domain.models.InventoryItem;
import com.roberteftene.dc.invetory_service.dto.InventoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {
    InventoryDto toDto(InventoryItem item);
    List<InventoryDto> toDtoList(List<InventoryItem> items);
}
