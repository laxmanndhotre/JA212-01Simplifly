package com.simplyfly.controller;

import com.simplyfly.dto.BookingDTO;
import com.simplyfly.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        logger.info("Creating a booking for user ID: {} on route ID: {}", bookingDTO.getUserId(), bookingDTO.getRouteId());
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        logger.info("Booking created successfully with ID: {}", createdBooking.getBookingId());
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        logger.debug("Fetching all bookings.");
        List<BookingDTO> bookings = bookingService.getAllBookings();
        logger.info("Successfully fetched {} bookings.", bookings.size());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        logger.debug("Fetching booking with ID: {}", id);
        BookingDTO booking = bookingService.getBookingById(id);
        logger.info("Successfully fetched booking with ID: {}", id);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Integer id, @RequestBody BookingDTO bookingDTO) {
        logger.info("Updating booking with ID: {}", id);
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        logger.info("Booking with ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        logger.info("Deleting booking with ID: {}", id);
        bookingService.deleteBooking(id);
        logger.info("Booking with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
