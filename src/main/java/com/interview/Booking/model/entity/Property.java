package com.interview.Booking.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "property")
@NoArgsConstructor
@AllArgsConstructor
public class Property extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Column
    private String name;

    @Column(name = "owner_name")
    private String ownerName;

    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Booking> bookings = new HashSet<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setProperty(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setProperty(null);
    }
}
