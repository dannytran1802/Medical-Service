package com.example.mobileapp.itf;

import com.example.mobileapp.model.Product;

import java.util.List;

public interface ProductInterface {

    void onSuccess(List<Product> productList);

    void onError(List<String> errors);

}
