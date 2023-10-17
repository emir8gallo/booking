package com.interview.Booking;

import com.interview.Booking.model.BookingType;
import com.interview.Booking.model.dto.BookingRequest;
import com.interview.Booking.model.dto.BookingResponse;
import com.interview.Booking.model.entity.Booking;
import com.interview.Booking.model.entity.Property;
import com.interview.Booking.repository.BookingRepository;
import com.interview.Booking.repository.PropertyRepository;
import com.interview.Booking.service.PropertyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

@SpringBootTest
class BookingApplicationTests {

	@MockBean
	BookingRepository bookingRepository;

	@MockBean
	PropertyRepository propertyRepository;

	@Autowired
	PropertyManagementService propertyManagementService;

	protected BookingRequest createMockBookingRequest() {
		return BookingRequest.builder()
				.startDate(LocalDate.now().plusDays(5))
				.endDate(LocalDate.now().plusDays(10))
				.type(BookingType.BOOK.getValue())
				.guestNumber(2)
				.mainGuestName("Test Guest")
				.propertyUuid(UUID.randomUUID())
				.build();
	}

	protected BookingRequest createMockBlockRequest() {
		return BookingRequest.builder()
				.startDate(LocalDate.now().plusDays(5))
				.endDate(LocalDate.now().plusDays(10))
				.type(BookingType.BLOCK.getValue())
				.guestNumber(2)
				.mainGuestName("Test Guest")
				.propertyUuid(UUID.randomUUID())
				.build();
	}

	protected BookingResponse createMockBookingResponse() {
		return BookingResponse.builder()
				.bookingUuid(UUID.randomUUID())
				.startDate(LocalDate.now().plusDays(5))
				.endDate(LocalDate.now().plusDays(10))
				.guestNumber(2)
				.mainGuestName("Test Guest")
				.build();
	}

	protected Property createMockProperty() {
		return Property.builder()
				.name("Test property")
				.ownerName("Test Owner")
				.bookings(new HashSet<>())
				.build();
	}

	protected Booking createMockBooking() {
		return Booking.builder()
				.type(BookingType.BOOK)
				.build();
	}

	protected Booking createMockBlock() {
		return Booking.builder()
				.type(BookingType.BLOCK)
				.build();
	}
}
