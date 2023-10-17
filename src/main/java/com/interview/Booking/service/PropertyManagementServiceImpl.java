package com.interview.Booking.service;

import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.dto.BookingResponse;
import com.interview.Booking.model.entity.Booking;
import com.interview.Booking.model.entity.Property;
import com.interview.Booking.repository.BookingRepository;
import com.interview.Booking.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PropertyManagementServiceImpl implements PropertyManagementService{

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    BookingFactory bookingFactory;

    @Override
    public BookingResponse book(BookingRequest bookingRequest) throws Exception {
        BookingService bookingService = bookingFactory.determineBookingType(bookingRequest);

        bookingService.validateBooking(bookingRequest);

        Property property = propertyRepository.findByUuid(bookingRequest.getPropertyUuid()).orElseThrow(() -> new EntityNotFoundException("Requested property not found"));

        Booking booking = bookingService.createEntity(bookingRequest, property);

        bookingRepository.save(booking);

        return bookingRepository.findBookingResponseByUuid(booking.getUuid());
    }

    @Override
    @Transactional
    public BookingResponse updateBooking(UUID bookingUuid, BookingRequest bookingRequest) throws Exception {
        BookingService bookingService = bookingFactory.determineBookingType(bookingRequest);

        bookingService.validateBooking(bookingRequest);

        Booking booking = bookingService.updateEntity(bookingUuid, bookingRequest);

        bookingRepository.save(booking);

        return bookingRepository.findBookingResponseByUuid(bookingUuid);
    }

    @Override
    @Transactional
    public void deleteBooking(UUID bookingUuid) {
        bookingRepository.deleteByUuid(bookingUuid);
    }
}
