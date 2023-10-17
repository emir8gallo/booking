package com.interview.Booking.service;

import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.dto.BookingResponse;

import java.util.UUID;

public interface PropertyManagementService {
    BookingResponse book(BookingRequest bookingRequest) throws Exception;
    BookingResponse updateBooking(UUID bookingUuid, BookingRequest bookingRequest) throws Exception;
    void deleteBooking(UUID bookingUuid);
}
