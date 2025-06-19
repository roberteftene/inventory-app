package com.roberteftene.dc.invetory_service.service;

import com.roberteftene.dc.invetory_service.domain.models.Product;
import com.roberteftene.dc.invetory_service.domain.repository.ProductRepository;
import com.roberteftene.dc.invetory_service.dto.ProductDto;
import com.roberteftene.dc.invetory_service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product entity = productMapper.toEntity(productDto);
        Product newProduct = this.productRepository.save(entity);
        return productMapper.toDto(newProduct);
    }
}
