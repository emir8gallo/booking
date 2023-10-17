package com.interview.Booking.model.entity;

import com.interview.Booking.model.BookingType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "booking")
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "main_guest_name")
    private String guestName;

    @Column(name = "guest_number")
    private Integer guestNumber;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    BookingType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if(!(o instanceof Booking)) return false;

        return id != null && id.equals(((Booking) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
