package com.interview.Booking;

import com.interview.Booking.model.BookingType;
import com.interview.Booking.model.dto.BookingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class CreateBookTest extends BookingApplicationTests {
    @Test
    void testCreateBookWithWrongBookingTypeException() {
        BookingRequest request = createMockBookingRequest();
        request.setType("test123");

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> propertyManagementService.book(request));
    }

    @Test
    void testCreateBookingFieldsMissingException() {
        BookingRequest request = new BookingRequest();
        request.setType(BookingType.BOOK.getValue());

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Fields must be populated", exception.getMessage());
    }

    @Test
    void testCreateBlockFieldsMissingException() {
        BookingRequest request = new BookingRequest();
        request.setType(BookingType.BLOCK.getValue());

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Fields must be populated", exception.getMessage());
    }

    @Test
    void testCreateBookingDateInPastException() {
        BookingRequest request = createMockBookingRequest();
        request.setStartDate(LocalDate.now().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("You cannot book in the past", exception.getMessage());
    }

    @Test
    void testCreateBlockDateInPastException() {
        BookingRequest request = createMockBlockRequest();
        request.setStartDate(LocalDate.now().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("You cannot book in the past", exception.getMessage());
    }

    @Test
    void testCreateBookingEndDateBeforeStartDateException() {
        BookingRequest request = createMockBookingRequest();
        request.setEndDate(request.getStartDate().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    void testCreateBlockEndDateBeforeStartDateException() {
        BookingRequest request = createMockBlockRequest();
        request.setEndDate(request.getStartDate().minusDays(1));

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    void testCreateBookingPropertyBookedException() {
        Mockito.when(bookingRepository.isPropertyReserved(any(), any(), any())).thenReturn(true);

        BookingRequest request = createMockBookingRequest();

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Property is booked during this period", exception.getMessage());
    }

    @Test
    void testCreateBookingPropertyNotFoundException() {
        Mockito.when(bookingRepository.isPropertyReserved(any(), any(), any())).thenReturn(false);

        BookingRequest request = createMockBookingRequest();

        Exception exception = Assertions.assertThrows(
                Exception.class,
                () -> propertyManagementService.book(request));

        Assertions.assertEquals("Requested property not found", exception.getMessage());
    }

    @Test
    void testCreateBooking() throws Exception {
        Mockito.when(propertyRepository.findByUuid(any())).thenReturn(Optional.ofNullable(createMockProperty()));
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());

        var bookingResponse = propertyManagementService.book(createMockBookingRequest());

        Assertions.assertEquals(bookingResponse.getStartDate(), LocalDate.now().plusDays(5));
        Assertions.assertEquals(bookingResponse.getEndDate(), LocalDate.now().plusDays(10));
        Assertions.assertEquals(bookingResponse.getGuestNumber(), 2);
        Assertions.assertEquals(bookingResponse.getMainGuestName(), "Test Guest");
        Assertions.assertNotNull(bookingResponse.getBookingUuid());
    }

    @Test
    void testCreateBlocking() throws Exception {
        Mockito.when(propertyRepository.findByUuid(any())).thenReturn(Optional.ofNullable(createMockProperty()));
        Mockito.when(bookingRepository.findBookingResponseByUuid(any())).thenReturn(createMockBookingResponse());

        var bookingResponse = propertyManagementService.book(createMockBlockRequest());

        Assertions.assertEquals(bookingResponse.getStartDate(), LocalDate.now().plusDays(5));
        Assertions.assertEquals(bookingResponse.getEndDate(), LocalDate.now().plusDays(10));
        Assertions.assertEquals(bookingResponse.getGuestNumber(), 2);
        Assertions.assertEquals(bookingResponse.getMainGuestName(), "Test Guest");
        Assertions.assertNotNull(bookingResponse.getBookingUuid());
    }
}
