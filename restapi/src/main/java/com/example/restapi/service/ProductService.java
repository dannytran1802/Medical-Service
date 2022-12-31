package com.example.restapi.service;

import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);

    List<Product> findByName(String name);

    Product save(Product product);

    List<Product> findByPharmacy(Pharmacy pharmacy);

}
