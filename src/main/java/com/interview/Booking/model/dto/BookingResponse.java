package com.interview.Booking.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private UUID bookingUuid;
    private String propertyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mainGuestName;
    private Integer guestNumber;
}
