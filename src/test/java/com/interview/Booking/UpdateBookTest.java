package com.interview.Booking;

import com.interview.Booking.model.BookingType;
import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.dto.BookingResponse;
import com.interview.Booking.model.entity.Booking;
import com.interview.Booking.model.entity.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

public class UpdateBookTest extends BookingApplicationTests {
    @Test
    void testUpdateBookWithWrongBookingTypeException() {
        BookingRequest request = createMockBookingRequest();
        request.setType("test123");

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));
    }

    @Test
    void testUpdateBookingFieldsMissingException() {
        BookingRequest request = new BookingRequest();
        request.setType(BookingType.BOOK.getValue());

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Fields must be populated", exception.getMessage());
    }

    @Test
    void testUpdateBlockFieldsMissingException() {
        BookingRequest request = new BookingRequest();
        request.setType(BookingType.BLOCK.getValue());

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Fields must be populated", exception.getMessage());
    }

    @Test
    void testUpdateBookingDateInPastException() {
        BookingRequest request = createMockBookingRequest();
        request.setStartDate(LocalDate.now().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("You cannot book in the past", exception.getMessage());
    }

    @Test
    void testUpdateBlockDateInPastException() {
        BookingRequest request = createMockBlockRequest();
        request.setStartDate(LocalDate.now().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("You cannot book in the past", exception.getMessage());
    }

    @Test
    void testUpdateBookingEndDateBeforeStartDateException() {
        BookingRequest request = createMockBookingRequest();
        request.setEndDate(request.getStartDate().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    void testUpdateBlockEndDateBeforeStartDateException() {
        BookingRequest request = createMockBlockRequest();
        request.setEndDate(request.getStartDate().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    void testUpdateBookingPropertyBookedException() {
        Mockito.when(bookingRepository.isPropertyReserved(any(), any(), any())).thenReturn(true);

        BookingRequest request = createMockBookingRequest();

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Property is booked during this period", exception.getMessage());
    }

    @Test
    void testUpdateBookingNotFoundException() {
        Mockito.when(bookingRepository.isPropertyReserved(any(), any(), any())).thenReturn(false);

        BookingRequest request = createMockBookingRequest();

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Requested booking not found", exception.getMessage());
    }

    @Test
    void testUpdateBlockNotFoundException() {
        Mockito.when(bookingRepository.isPropertyReserved(any(), any(), any())).thenReturn(false);

        BookingRequest request = createMockBlockRequest();

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), request));

        Assertions.assertEquals("Requested booking not found", exception.getMessage());
    }

    @Test
    void testUpdateBookingWrongTypeException() {
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());
        Mockito.when(bookingRepository.findBookingByUuid(any())).thenReturn(Optional.ofNullable(createMockBlock()));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), createMockBookingRequest()));

        Assertions.assertEquals("Different types", exception.getMessage());
    }

    @Test
    void testUpdateBlockWrongTypeException() {
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());
        Mockito.when(bookingRepository.findBookingByUuid(any())).thenReturn(Optional.ofNullable(createMockBooking()));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.updateBooking(UUID.randomUUID(), createMockBlockRequest()));

        Assertions.assertEquals("Different types", exception.getMessage());
    }

    @Test
    void testUpdateBooking() throws Exception {
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());
        Mockito.when(bookingRepository.findBookingByUuid(any())).thenReturn(Optional.ofNullable(createMockBooking()));


        var bookingResponse = propertyManagementService.updateBooking(UUID.randomUUID(), createMockBookingRequest());

        Assertions.assertEquals(bookingResponse.getStartDate(), LocalDate.now().plusDays(5));
        Assertions.assertEquals(bookingResponse.getEndDate(), LocalDate.now().plusDays(10));
        Assertions.assertEquals(bookingResponse.getGuestNumber(), 2);
        Assertions.assertEquals(bookingResponse.getMainGuestName(), "Test Guest");
        Assertions.assertNotNull(bookingResponse.getBookingUuid());
    }

    @Test
    void testUpdateBlocking() throws Exception {
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());
        Mockito.when(bookingRepository.findBookingByUuid(any())).thenReturn(Optional.ofNullable(createMockBlock()));


        var bookingResponse = propertyManagementService.updateBooking(UUID.randomUUID(),createMockBlockRequest());

        Assertions.assertEquals(bookingResponse.getStartDate(), LocalDate.now().plusDays(5));
        Assertions.assertEquals(bookingResponse.getEndDate(), LocalDate.now().plusDays(10));
        Assertions.assertEquals(bookingResponse.getGuestNumber(), 2);
        Assertions.assertEquals(bookingResponse.getMainGuestName(), "Test Guest");
        Assertions.assertNotNull(bookingResponse.getBookingUuid());
    }
}
