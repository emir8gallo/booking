package com.interview.Booking.service;

import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.entity.Booking;
import com.interview.Booking.model.entity.Property;

import java.util.UUID;

public interface BookingService {
    void validateBooking(BookingRequest bookingRequest) throws Exception;
    Booking createEntity(BookingRequest bookingRequest, Property property);
    Booking updateEntity(UUID bookingUuid, BookingRequest bookingRequest);
}
