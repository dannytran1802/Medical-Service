package com.example.mobileapp.api;

import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.CheckoutInterface;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutAPI {

    private CheckoutInterface checkoutInterface = null;

    private APIService apiService;

    public CheckoutAPI(CheckoutInterface checkoutInterface) {
        this.checkoutInterface = checkoutInterface;
        apiService = APIClient.getAPIService();
    }

    public void saveOrder(OrdersDTO ordersDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.checkoutOrder(token, ordersDTO).enqueue(new Callback<ReponseDTO>() {
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
                        checkoutInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        checkoutInterface.onSuccessOrder();
                    }
                } else {
                    checkoutInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                checkoutInterface.onError(null);
            }
        });
    }

    public void findAllOrdersByAccount(long accountId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllOrdersByAccount(token, accountId).enqueue(new Callback<ReponseDTO<List<Orders>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Orders>>> call, Response<ReponseDTO<List<Orders>>> response) {
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
                        checkoutInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Orders> ordersList = response.body().getData();
                        checkoutInterface.onFetchOrders(ordersList);
                    }
                } else {
                    checkoutInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Orders>>> call, Throwable t) {
                checkoutInterface.onError(null);
            }
        });
    }

    public void findAllOrdersByPharmacy(long pharmacyId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllOrdersByPharmacy(token, pharmacyId).enqueue(new Callback<ReponseDTO<List<Orders>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Orders>>> call, Response<ReponseDTO<List<Orders>>> response) {
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
                        checkoutInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Orders> ordersList = response.body().getData();
                        checkoutInterface.onFetchOrders(ordersList);
                    }
                } else {
                    checkoutInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Orders>>> call, Throwable t) {
                checkoutInterface.onError(null);
            }
        });
    }

    public void updateOrderProgress(OrdersDTO ordersDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.updateOrderProgress(token, ordersDTO).enqueue(new Callback<ReponseDTO>() {
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
                        checkoutInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        checkoutInterface.onSuccessOrder();
                    }
                } else {
                    checkoutInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                checkoutInterface.onError(null);
            }
        });
    }

    public void findBooking(BookingDTO bookingDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findBooking(token, bookingDTO).enqueue(new Callback<ReponseDTO<BookingDTO>>() {
            @Override
            public void onResponse(Call<ReponseDTO<BookingDTO>> call, Response<ReponseDTO<BookingDTO>> response) {
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
                        checkoutInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        checkoutInterface.onSuccessBooking();
                    }
                } else {
                    checkoutInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<BookingDTO>> call, Throwable t) {
                checkoutInterface.onError(null);
            }
        });
    }

}
