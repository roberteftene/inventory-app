package com.roberteftene.dc.invetory_service.domain.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(exclude = {"product", "distributionCenter", "order"})
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantityRequested;

    private Integer quantityFulfilled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribution_center_id", nullable = false)
    private DistributionCenter distributionCenter;
}

