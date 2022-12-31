package com.example.mobileapp.api;

import com.example.mobileapp.dto.BookingDTO;
import com.example.mobileapp.dto.RegisterDTO;
import com.example.mobileapp.dto.ReponseDTO;
import com.example.mobileapp.itf.BookingInterface;
import com.example.mobileapp.itf.RegisterInterface;
import com.example.mobileapp.model.Booking;
import com.example.mobileapp.util.ContantUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingAPI {

    private BookingInterface bookingInterface = null;

    private APIService apiService;

    public BookingAPI(BookingInterface bookingInterface) {
        this.bookingInterface = bookingInterface;
        apiService = APIClient.getAPIService();
    }

    public void findBookingPending(long accountId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findBookingPending(token, accountId).enqueue(new Callback<ReponseDTO>() {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        bookingInterface.onBookingPending();
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

    public void findAllBookingByAccount(long accountId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllBookingByAccount(token, accountId).enqueue(new Callback<ReponseDTO<List<Booking>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Booking>>> call, Response<ReponseDTO<List<Booking>>> response) {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Booking> bookingList = (List<Booking>) reponseDTO.getData();
                        bookingInterface.onListBooking(bookingList);
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Booking>>> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

    public void findAllBookingByAmbulance(long ambulanceId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllBookingByAmbulance(token, ambulanceId).enqueue(new Callback<ReponseDTO<List<Booking>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Booking>>> call, Response<ReponseDTO<List<Booking>>> response) {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Booking> bookingList = (List<Booking>) reponseDTO.getData();
                        bookingInterface.onListBooking(bookingList);
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Booking>>> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

    public void saveBooking(BookingDTO bookingDTO) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.saveBooking(token, bookingDTO).enqueue(new Callback<ReponseDTO<Booking>>() {
            @Override
            public void onResponse(Call<ReponseDTO<Booking>> call, Response<ReponseDTO<Booking>> response) {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Booking> bookingList = new ArrayList<>();
                        bookingList.add((Booking) reponseDTO.getData());
                        bookingInterface.onListBooking(bookingList);
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<Booking>> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

    public void findAllBookingByAmbulanceAndProgress(long ambulanceId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.findAllBookingByAmbulanceAndProgress(token, ambulanceId).enqueue(new Callback<ReponseDTO<List<Booking>>>() {
            @Override
            public void onResponse(Call<ReponseDTO<List<Booking>>> call, Response<ReponseDTO<List<Booking>>> response) {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Booking> bookingList = (List<Booking>) reponseDTO.getData();
                        bookingInterface.onListBooking(bookingList);
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO<List<Booking>>> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

    public void completed(long bookingId) {
        String token = "bearer " + ContantUtil.accessToken;
        apiService.bookingCompleted(token, bookingId).enqueue(new Callback<ReponseDTO>() {
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
                        bookingInterface.onError(errors);
                    }

                    if (reponseDTO.getStatus().equals(ReponseDTO.TypeResult.SUCCESS)) {
                        List<Booking> bookingList = new ArrayList<>();
                        bookingInterface.onListBooking(bookingList);
                    }
                } else {
                    bookingInterface.onError(null);
                }
            }

            @Override
            public void onFailure(Call<ReponseDTO> call, Throwable t) {
                bookingInterface.onError(null);
            }
        });
    }

}
