package com.simplyfly.service;

import com.simplyfly.dto.BookingDTO;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Integer bookingId);
    BookingDTO updateBooking(Integer bookingId, BookingDTO bookingDTO);
    void deleteBooking(Integer bookingId);
}
