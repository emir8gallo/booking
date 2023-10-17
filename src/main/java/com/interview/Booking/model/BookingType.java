package com.interview.Booking.model;

public enum BookingType {
    BOOK("booking"),
    BLOCK("block");

    private final String value;

    BookingType(String value) {
        this.value = value;
    }

    public static BookingType fromValue(String value) {
        for (BookingType e : BookingType.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public String getValue() {
        return value;
    }
}
