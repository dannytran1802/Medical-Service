package com.example.restapi.repository;

import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByPharmacyAndStatusIsTrue(Pharmacy pharmacy);

}