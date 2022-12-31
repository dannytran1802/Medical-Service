package com.example.restapi.controller;

import com.example.restapi.model.dto.ProductDTO;
import com.example.restapi.model.dto.RestReponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.entity.Product;
import com.example.restapi.model.mapper.ProductMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.service.ProductService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping("/list")
    public ResponseEntity<RestReponseDTO> findAll() {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Product> products = productService.findAll();

        if (products == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(productMapper.toListDTO(products));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> findById(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();
        Product product = productService.findById(id);

        if (product == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(product);
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RestReponseDTO> findByName(@PathVariable String name) {
        RestReponseDTO restResponse = new RestReponseDTO();
        List<Product> products = productService.findByName(name);

        if (products == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
        }

        restResponse.ok(products);
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/pharmacy/{id}")
    public ResponseEntity<RestReponseDTO> findByPharmacy(@PathVariable long id) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Pharmacy pharmacy = pharmacyService.findById(id);
        if (pharmacy == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }

        List<Product> products = productService.findByPharmacy(pharmacy);
        restResponse.ok(productMapper.toListDTO(products));
        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestReponseDTO> save(@RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        RestReponseDTO restResponse = new RestReponseDTO();

        Pharmacy pharmacy = pharmacyService.findById(productDTO.getPharmacyId());

        if (pharmacy == null) {
            restResponse.fail();
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        } else {
            productValidator.validate(productDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
                return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
            } else{
                Product product = new Product();
                productMapper.toEntity(product, productDTO);
                product.setPharmacy(pharmacy);
                product.setStatus(true);
                productService.save(product);

                restResponse.ok(product);
                return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
            }
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<RestReponseDTO> delete(@PathVariable long id){
        RestReponseDTO restResponse = new RestReponseDTO();
        Product product = productService.findById(id);

        if (product != null) {
            product.setStatus(false);
            productService.save(product);
            restResponse.ok(productMapper.toDTO(product));
            return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.OK);
        }

        return new ResponseEntity<RestReponseDTO>(restResponse, HttpStatus.NOT_FOUND);
    }

}
