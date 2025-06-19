package com.roberteftene.dc.invetory_service.domain.models;

import com.roberteftene.dc.invetory_service.domain.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    private String description;

    private Double  price;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}
