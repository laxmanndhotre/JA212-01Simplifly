package com.simplyfly.service.impl;

import com.simplyfly.dto.BookingDTO;
import com.simplyfly.entity.Booking;
import com.simplyfly.entity.Route;
import com.simplyfly.entity.User;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.exception.InvalidRequestException;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.repository.UserRepository;
import com.simplyfly.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        logger.info("Attempting to create booking for user ID: {} on route ID: {}.", bookingDTO.getUserId(), bookingDTO.getRouteId());

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found.", bookingDTO.getUserId());
                    return new ResourceNotFoundException("User with ID " + bookingDTO.getUserId() + " not found");
                });

        Route route = routeRepository.findById(bookingDTO.getRouteId())
                .orElseThrow(() -> {
                    logger.warn("Route with ID {} not found.", bookingDTO.getRouteId());
                    return new ResourceNotFoundException("Route with ID " + bookingDTO.getRouteId() + " not found");
                });

        if (bookingDTO.getSeatsBooked() > route.getAvailableSeats()) {
            logger.error("Booking failed. Not enough available seats on route ID: {}.", bookingDTO.getRouteId());
            throw new InvalidRequestException("Not enough available seats on the route.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoute(route);
        booking.setSeatsBooked(bookingDTO.getSeatsBooked());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setStatus(Booking.Status.valueOf(bookingDTO.getStatus()));
        booking.setBookingDate(java.time.LocalDateTime.now());

        route.setAvailableSeats(route.getAvailableSeats() - bookingDTO.getSeatsBooked());
        routeRepository.save(route);

        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking created successfully with ID: {}.", savedBooking.getBookingId());

        return mapToDTO(savedBooking);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        logger.debug("Fetching all bookings.");

        List<BookingDTO> bookings = bookingRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());

        logger.info("Successfully fetched {} bookings.", bookings.size());
        return bookings;
    }

    @Override
    public BookingDTO getBookingById(Integer bookingId) {
        logger.debug("Fetching booking with ID: {}.", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.warn("Booking with ID {} not found.", bookingId);
                    return new ResourceNotFoundException("Booking with ID " + bookingId + " not found");
                });

        logger.info("Successfully fetched booking with ID: {}.", bookingId);
        return mapToDTO(booking);
    }

    @Override
    public BookingDTO updateBooking(Integer bookingId, BookingDTO bookingDTO) {
        logger.info("Updating booking with ID: {}.", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.warn("Booking with ID {} not found.", bookingId);
                    return new ResourceNotFoundException("Booking with ID " + bookingId + " not found");
                });

        Route route = routeRepository.findById(bookingDTO.getRouteId())
                .orElseThrow(() -> {
                    logger.warn("Route with ID {} not found.", bookingDTO.getRouteId());
                    return new ResourceNotFoundException("Route with ID " + bookingDTO.getRouteId() + " not found");
                });

        if (bookingDTO.getSeatsBooked() > route.getAvailableSeats()) {
            logger.error("Update failed. Not enough available seats on route ID: {}.", bookingDTO.getRouteId());
            throw new InvalidRequestException("Not enough available seats on the route.");
        }

        booking.setSeatsBooked(bookingDTO.getSeatsBooked());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setStatus(Booking.Status.valueOf(bookingDTO.getStatus()));

        Booking updatedBooking = bookingRepository.save(booking);
        logger.info("Booking with ID: {} updated successfully.", bookingId);

        return mapToDTO(updatedBooking);
    }

    @Override
    public void deleteBooking(Integer bookingId) {
        logger.info("Attempting to delete booking with ID: {}.", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.warn("Booking with ID {} not found.", bookingId);
                    return new ResourceNotFoundException("Booking with ID " + bookingId + " not found");
                });

        bookingRepository.delete(booking);
        logger.info("Booking with ID: {} deleted successfully.", bookingId);
    }

    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setUserId(booking.getUser().getUserId());
        bookingDTO.setRouteId(booking.getRoute().getRouteId());
        bookingDTO.setSeatsBooked(booking.getSeatsBooked());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setStatus(booking.getStatus().name());
        bookingDTO.setBookingDate(booking.getBookingDate().toString());
        return bookingDTO;
    }
}
