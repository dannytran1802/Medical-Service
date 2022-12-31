package com.example.mobileapp.api;

import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.ProductInterface;
import com.example.mobileapp.dto.ProductDTO;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAPI {

    private ProductInterface productInterface = null;

    private APIService apiService;

    public ProductAPI(ProductInterface productInterface) {
        this.productInterface = productInterface;
        apiService = APIClient.getAPIService();
    }

    public void findAllProductByPharmacy(long pharmacyId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllProduct(token, pharmacyId).enqueue(new Callback<ReponseDTO<List<Product>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Product>>> call, Response<ReponseDTO<List<Product>>> response) {
                if (response.code() == 200) {
                    ReponseDTO reponseDTO = response.body();
                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.ERROR)) {
                        Map<String, Object> messages = response.body().getMessages();
                        List<String> errors = new ArrayList<>();
                        if (messages != null) {
                            for (Map.Entry<String, Object> entry : messages.entrySet()) {
                                errors.add(entry.getValue().toString());
                            }
                        }
                        productInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Product> productList = (List<Product>) reponseDTO.getData();
                        productInterface.onSuccess(productList);
                    }
                } else {
                    productInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Product>>> call, Throwable t) {
                productInterface.onError(null);
            }
        });
    }

    public void updateProduct(ProductDTO productDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.save(token, productDTO).enqueue(new Callback<ReponseDTO<Product>>() {
            @Override
            public void onResponse(Call<ReponseDTO<Product>> call, Response<ReponseDTO<Product>> response) {
                if (response.code() == 200) {
                    ReponseDTO reponseDTO = response.body();
                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.ERROR)) {
                        Map<String, Object> messages = response.body().getMessages();
                        List<String> errors = new ArrayList<>();
                        if (messages != null) {
                            for (Map.Entry<String, Object> entry : messages.entrySet()) {
                                errors.add(entry.getValue().toString());
                            }
                        }
                        productInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        Product product = (Product) reponseDTO.getData();
                        List<Product> productList = new ArrayList<>();
                        productList.add(product);
                        productInterface.onSuccess(productList);
                    }
                } else {
                    productInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<Product>> call, Throwable t) {
                productInterface.onError(null);
            }
        });
    }

    public void deleteProduct(long productId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.deleteProduct(token, productId).enqueue(new Callback<ReponseDTO>() {
            @Override
            public void onResponse(Call<ReponseDTO> call, Response<ReponseDTO> response) {
                if (response.code() == 200) {
                    ReponseDTO reponseDTO = response.body();
                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.ERROR)) {
                        Map<String, Object> messages = response.body().getMessages();
                        List<String> errors = new ArrayList<>();
                        if (messages != null) {
                            for (Map.Entry<String, Object> entry : messages.entrySet()) {
                                errors.add(entry.getValue().toString());
                            }
                        }
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
            }
        });
    }

}
