package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.PharmacyDTO;
import com.example.restapi.model.dto.ProductDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Pharmacy;
import com.example.restapi.model.entity.Product;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.model.mapper.ProductMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.service.PharmacyService;
import com.example.restapi.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setAvatar(product.getAvatar());
        productDTO.setPrice(FormatUtil.formatNumber(product.getPrice()));
        productDTO.setDescription(product.getDescription());
        productDTO.setOtc(product.isOtc());
        productDTO.setStatus(product.isStatus());

        if (product.getPharmacy().getId() > 0) {
            PharmacyDTO pharmacyDTO = new PharmacyDTO();
            pharmacyDTO.setId(product.getPharmacy().getId());
            pharmacyDTO.setAddress(product.getPharmacy().getAddress());
            pharmacyDTO.setName(product.getPharmacy().getName());

            pharmacyDTO.setAccountDTO(accountMapper.toDTO(product.getPharmacy().getAccount()));
            pharmacyDTO.setAccountId(product.getPharmacy().getAccount().getId());

            productDTO.setPharmacy(pharmacyDTO);
            productDTO.setPharmacyId(pharmacyDTO.getId());
        }

        return productDTO;
    }

    @Override
    public List<ProductDTO> toListDTO(List<Product> products) {
        if (products == null) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<>(products.size());
        for (Product product : products) {
            ProductDTO productDTO = toDTO(product);
            if (productDTO != null) {
                list.add(productDTO);
            }
        }

        return list;
    }

    @Override
    public Product toEntity(Product product, ProductDTO productDTO) {

        if (productDTO == null) {
            return null;
        }

        if (product == null) {
            product = new Product();
        }

        product.setName(productDTO.getName());
        product.setAvatar(productDTO.getAvatar());
        product.setPrice(FormatUtil.formatNumber(productDTO.getPrice()));
        product.setStatus(productDTO.isStatus());
        product.setDescription(productDTO.getDescription());
        product.setOtc(productDTO.isOtc());

        if (productDTO.getPharmacyId() > 0) {
            Pharmacy pharmacy = pharmacyService.findById(productDTO.getPharmacyId());
            if (pharmacy != null) {
                product.setPharmacy(pharmacy);
            }
        }

        return product;
    }
}
