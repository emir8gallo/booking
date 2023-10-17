package com.interview.Booking.service;

import com.interview.Booking.model.dto.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookingFactory {
    @Autowired
    BookingServiceImpl bookingService;

    @Autowired
    BlockServiceImpl blockService;

    public BookingService determineBookingType(BookingRequest bookingRequest) {
        switch (bookingRequest.getType()) {
            case "booking" -> {
                return bookingService;
            }
            case "block" -> {
                return blockService;
            }
            default -> throw new IllegalArgumentException("Invalid Booking type");
        }
    }
}
