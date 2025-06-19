package com.roberteftene.dc.invetory_service.domain.repository;

import com.roberteftene.dc.invetory_service.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
