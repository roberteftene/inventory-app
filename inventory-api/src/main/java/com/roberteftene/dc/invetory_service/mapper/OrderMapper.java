package com.roberteftene.dc.invetory_service.mapper;

import com.roberteftene.dc.invetory_service.domain.models.Order;
import com.roberteftene.dc.invetory_service.dto.OrderCreationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderCreationResponse toOrderCreationResponse(Order order);


}
