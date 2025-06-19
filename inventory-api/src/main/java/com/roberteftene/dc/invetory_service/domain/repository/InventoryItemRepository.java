package com.roberteftene.dc.invetory_service.domain.repository;

import com.roberteftene.dc.invetory_service.domain.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByDistributionCenterIdAndProductSku(Long dcId, String sku);

    @Query("""
                 SELECT i.quantity
                 FROM InventoryItem i
                 WHERE i.product.id = :productId
                   AND i.distributionCenter.id = :dcId
            """)
    int getInventoryQuantityFor(Long dcId, Long productId);

    Optional<InventoryItem> findByDistributionCenterIdAndProductId(Long dcId, Long productId);
}