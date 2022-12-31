package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.ProductDTO;
import com.example.restapi.model.entity.Product;

import java.util.List;

public interface ProductMapper {

    // Map Entity to DTO
    ProductDTO toDTO(Product product);

    List<ProductDTO> toListDTO(List<Product> products);

    // Map DTO to Entity
    Product toEntity(Product product, ProductDTO productDTO);
    
}
