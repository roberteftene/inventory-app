package com.roberteftene.dc.invetory_service.domain.repository;

import com.roberteftene.dc.invetory_service.domain.models.Order;
import com.roberteftene.dc.invetory_service.domain.repository.projection.OrderFlatView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
                SELECT
                  o.id as orderId,
                  o.status as status,
                  o.requestedAt as requestedAt,
                  oi.id as orderItemId,
                  p.id as productId,
                  p.name as productName,
                  oi.quantityRequested as quantityRequested,
                  oi.quantityFulfilled as quantityFulfilled,
                  dc.id as distributionCenterId
                FROM Order o
                JOIN o.orderItems oi
                JOIN oi.product p
                JOIN oi.distributionCenter dc
            """)
    List<OrderFlatView> findOrdersViews();


}

