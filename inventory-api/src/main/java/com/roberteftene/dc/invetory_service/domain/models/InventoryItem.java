package com.roberteftene.dc.invetory_service.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = {"product", "distributionCenter"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "inventory_item", indexes = {
        @Index(name = "idx_inventory_dc", columnList = "distribution_center_id")
})
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribution_center_id")
    private DistributionCenter distributionCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Positive
    private Integer quantity;

    private LocalDate expiresAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
