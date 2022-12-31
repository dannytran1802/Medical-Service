package com.example.mobileapp.api;

import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.dto.AuthDTO;
import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.LocationDTO;
import com.example.mobileapp.dto.OrdersDTO;
import com.example.mobileapp.dto.ProductDTO;
import com.example.mobileapp.dto.ProfileDTO;
import com.example.mobileapp.dto.RegisterDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.model.Orders;
import com.example.mobileapp.model.Pharmacy;
import com.example.mobileapp.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @Headers({"Authorization: Basic cmVzdGFwcDpwYXNzd29yZA==",
                    "Content-Type: application/x-www-form-urlencoded"})
    @POST("/oauth/token")
    Call<AuthDTO> loginAccount(@Field("username") String username, @Field("password") String password,
                               @Field("grant_type") String grantType);

    @POST("/api/auth/register")
    Call<ReponseDTO> registerAccount(@Body RegisterDTO registerDTO);

    @POST("/api/auth/forgot-pass")
    Call<ReponseDTO> forgotPasss(@Body AccountDTO accountDTO);

    @POST("/api/account/profile")
    Call<ReponseDTO<Account>> updateProfile(@Header("Authorization") String token, @Body ProfileDTO profileDTO);

    @GET("/api/account/profile/{id}")
    Call<ReponseDTO<Account>> findProfileByAccountId(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/pharmacy/list")
    Call<ReponseDTO<List<Pharmacy>>> findAllPharmacy(@Header("Authorization") String token);

    @GET("/api/product/pharmacy/{id}")
    Call<ReponseDTO<List<Product>>> findAllProductByPharmacy(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/checkout/order")
    Call<ReponseDTO> checkoutOrder(@Header("Authorization") String token, @Body OrdersDTO ordersDTO);

    @GET("/api/orders/account/{id}")
    Call<ReponseDTO<List<Orders>>> findAllOrdersByAccount(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/orders/pharmacy/{id}")
    Call<ReponseDTO<List<Orders>>> findAllOrdersByPharmacy(@Header("Authorization") String token, @Path("id") long id);
    
    @GET("/api/product/pharmacy/{id}")
    Call<ReponseDTO<List<Product>>> findAllProduct(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/product/save")
    Call<ReponseDTO<Product>> save(@Header("Authorization") String token, @Body ProductDTO productDTO);

    @POST("/api/orders/progress")
    Call<ReponseDTO> updateOrderProgress(@Header("Authorization") String token, @Body OrdersDTO ordersDTO);

    @POST("/api/checkout/booking/ambulance")
    Call<ReponseDTO<BookingDTO>> findBooking(@Header("Authorization") String token, @Body BookingDTO bookingDTO);

    @GET("/api/bookings/account/approved/{id}")
    Call<ReponseDTO> findBookingPending(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/bookings/account/{id}")
    Call<ReponseDTO<List<Booking>>> findAllBookingByAccount(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/bookings/ambulance/{id}")
    Call<ReponseDTO<List<Booking>>> findAllBookingByAmbulance(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/locations/status")
    Call<ReponseDTO> saveLocation(@Header("Authorization") String token, @Body LocationDTO locationDTO);

    @POST("/api/accounts/token")
    Call<ReponseDTO> saveToken(@Header("Authorization") String token, @Body AccountDTO accountDTO);

    @POST("/api/checkout/booking/update")
    Call<ReponseDTO<Booking>> saveBooking(@Header("Authorization") String token, @Body BookingDTO bookingDTO);

    @GET("/api/bookings/ambulance/progress/{id}")
    Call<ReponseDTO<List<Booking>>> findAllBookingByAmbulanceAndProgress(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/bookings/ambulance/progress/completed/{id}")
    Call<ReponseDTO> bookingCompleted(@Header("Authorization") String token, @Path("id") long id);

    @DELETE("/api/product/id/{id}")
    Call<ReponseDTO> deleteProduct(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/account/password")
    Call<ReponseDTO> changePass(@Header("Authorization") String token, @Body ProfileDTO profileDTO);

}
