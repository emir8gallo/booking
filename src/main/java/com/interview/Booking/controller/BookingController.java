package com.interview.Booking.controller;

import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.dto.BookingResponse;
import com.interview.Booking.service.PropertyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    PropertyManagementService propertyManagementService;

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest booking) throws Exception {
        return new ResponseEntity<>(propertyManagementService.book(booking), HttpStatus.OK);
    }

    @PutMapping("/book/{bookingUuid}")
    public ResponseEntity<BookingResponse> updateBook(
            @PathVariable UUID bookingUuid,
            @RequestBody BookingRequest booking
    ) throws Exception {
        return new ResponseEntity<>(propertyManagementService.updateBooking(bookingUuid, booking), HttpStatus.OK);
    }

    @DeleteMapping("/book/{bookingUuid}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable UUID bookingUuid) {
        propertyManagementService.deleteBooking(bookingUuid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
