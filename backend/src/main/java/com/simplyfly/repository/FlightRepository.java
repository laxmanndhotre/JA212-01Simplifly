package com.simplyfly.repository;

import com.simplyfly.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    boolean existsByFlightNumber(String flightNumber);
}
