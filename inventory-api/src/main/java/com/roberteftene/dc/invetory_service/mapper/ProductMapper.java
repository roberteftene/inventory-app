package com.roberteftene.dc.invetory_service.mapper;

import com.roberteftene.dc.invetory_service.domain.models.Product;
import com.roberteftene.dc.invetory_service.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}
