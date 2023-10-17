package com.interview.Booking.service;

import com.interview.Booking.model.BookingType;
import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.entity.Booking;
import com.interview.Booking.model.entity.Property;
import com.interview.Booking.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void validateBooking(BookingRequest bookingRequest) throws Exception {
        if(bookingRequest.getPropertyUuid() == null ||
                bookingRequest.getStartDate() == null || bookingRequest.getEndDate() == null ||
                bookingRequest.getMainGuestName() == null || bookingRequest.getGuestNumber() == null)
            throw new Exception("Fields must be populated");

        LocalDate today = LocalDate.now();

        if(today.isAfter(bookingRequest.getStartDate()) || today.isAfter(bookingRequest.getEndDate()))
            throw new Exception("You cannot book in the past");

        if(bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate()))
            throw new Exception("Start date must be before end date");

        if(bookingRequest.getType().equals(BookingType.BOOK.getValue()) && bookingRepository.isPropertyReserved(
                bookingRequest.getPropertyUuid(),
                bookingRequest.getStartDate(),
                bookingRequest.getEndDate())
        ) {
            throw new Exception("Property is booked during this period");
        }
    }

    @Override
    public Booking createEntity(BookingRequest bookingRequest, Property property) {
        Booking booking =  Booking.builder()
                .uuid(UUID.randomUUID())
                .startDate(bookingRequest.getStartDate())
                .endDate(bookingRequest.getEndDate())
                .guestName(bookingRequest.getMainGuestName())
                .guestNumber(bookingRequest.getGuestNumber())
                .type(BookingType.fromValue(bookingRequest.getType()))
                .build();

        property.addBooking(booking);

        return booking;
    }

    @Override
    public Booking updateEntity(UUID bookingUuid, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findBookingByUuid(bookingUuid).orElseThrow(() -> new EntityNotFoundException("Requested booking not found"));

        if(!booking.getType().equals(BookingType.fromValue(bookingRequest.getType())))
            throw new IllegalArgumentException("Different types");

        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setGuestName(bookingRequest.getMainGuestName());
        booking.setGuestNumber(bookingRequest.getGuestNumber());
        booking.setUpdatedAt(LocalDateTime.now());

        return booking;
    }
}
