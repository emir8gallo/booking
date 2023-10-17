package com.interview.Booking.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private UUID propertyUuid;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String mainGuestName;
    private Integer guestNumber;
}
