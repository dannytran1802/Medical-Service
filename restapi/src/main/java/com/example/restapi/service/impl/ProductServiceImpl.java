package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.entity.Product;
import com.example.restapi.repository.ProductRepository;
import com.example.restapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByPharmacy(Pharmacy pharmacy) {
        return productRepository.findByPharmacyAndStatusIsTrue(pharmacy);
    }

}
