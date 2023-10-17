package com.interview.Booking.repository;

import com.interview.Booking.model.dto.BookingResponse;
import com.interview.Booking.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(
            "SELECT new com.interview.Booking.model.dto.BookingResponse(b.uuid, p.name, b.startDate, b.endDate, b.guestName, b.guestNumber) " +
                "FROM Booking b " +
                "LEFT JOIN b.property p " +
                "WHERE b.uuid = :uuid"
    )
    BookingResponse findBookingResponseByUuid(UUID uuid);

    @Query(
            "SELECT count(b.id) > 0 " +
                    "FROM Booking b " +
                    "LEFT JOIN b.property p " +
                    "WHERE p.uuid = :propertyUuid AND b.startDate <= :endDate AND b.endDate >= :startDate"
    )
    boolean isPropertyReserved(UUID propertyUuid, LocalDate startDate, LocalDate endDate);

    Optional<Booking> findBookingByUuid(UUID bookingUuid);

    @Modifying
    @Query("UPDATE Booking SET deleted = true, deletedAt = CURRENT_TIMESTAMP where uuid = :bookingUuid")
    void deleteByUuid(UUID bookingUuid);
}
