package com.hrs.service;

import com.hrs.model.reponse.BookingResponse;

import java.util.List;

public interface BookingService {

    List<BookingResponse> getList(String token);

    List<BookingResponse> getListProgressPending(String token);

    BookingResponse getById(String token, long id);

}
