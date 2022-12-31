package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.BookingDTO;
import com.example.restapi.model.entity.Booking;

import java.util.List;

public interface BookingMapper {

    // Map Entity to DTO
    BookingDTO toDTO(Booking booking);

    List<BookingDTO> toListDTO(List<Booking> bookings);

    // Map DTO to Entity
    Booking toEntity(BookingDTO bookingDTO);
    
}
