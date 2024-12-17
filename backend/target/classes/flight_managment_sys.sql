-- Create the database
CREATE DATABASE flight_booking_system;

-- Use the database
USE flight_booking_system;

-- Create the Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role ENUM('PASSENGER', 'FLIGHT_OWNER', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data into Users table
INSERT INTO users (username, password, email, role) VALUES
('rahul123', 'hashed_password1', 'rahul.m@outlook.com', 'PASSENGER'),
('neha567', 'hashed_password2', 'neha.gupta@gmail.com', 'PASSENGER'),
('sachin789', 'hashed_password3', 'sachin.kumar@ymail.com', 'PASSENGER'),
('deepak321', 'hashed_password4', 'deepak.sharma@outlook.com', 'PASSENGER'),
('kriti987', 'hashed_password5', 'kriti.patel@rediffmail.com', 'PASSENGER'),
('airindia_owner', 'hashed_password6', 'owner@airindia.com', 'FLIGHT_OWNER'),
('spicejet_owner', 'hashed_password7', 'owner@spicejet.com', 'FLIGHT_OWNER'),
('indigo_owner', 'hashed_password8', 'owner@indigo.com', 'FLIGHT_OWNER'),
('admin1', 'hashed_password9', 'admin@flightportal.com', 'ADMIN'),
('admin2', 'hashed_password10', 'admin2@flightportal.com', 'ADMIN');

-- Create the Flights table
CREATE TABLE flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_name VARCHAR(100) NOT NULL,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    total_seats INT NOT NULL,
    fare DECIMAL(10, 2) NOT NULL,
    baggage_info VARCHAR(255),
    owner_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

-- Insert sample data into Flights table
INSERT INTO flights (flight_name, flight_number, total_seats, fare, baggage_info, owner_id) VALUES
('Air India', 'AI-101', 200, 5500.00, '25 kg', 6),
('SpiceJet', 'SJ-202', 180, 4000.00, '20 kg', 7),
('Indigo', '6E-345', 160, 3500.00, '15 kg', 8),
('Air India', 'AI-102', 220, 6000.00, '30 kg', 6),
('GoAir', 'G8-412', 150, 3300.00, '10 kg', 7);

-- Create the Routes table
CREATE TABLE routes (
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT,
    origin VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    available_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id)
);

-- Insert sample data into Routes table with updated future dates
INSERT INTO routes (flight_id, origin, destination, departure_time, arrival_time, available_seats) VALUES
(1, 'Delhi', 'Mumbai', '2025-01-12 09:00:00', '2025-01-12 11:30:00', 180),
(2, 'Mumbai', 'Bangalore', '2025-01-13 14:00:00', '2025-01-13 16:30:00', 160),
(3, 'Delhi', 'Kolkata', '2025-01-14 07:30:00', '2025-01-14 09:00:00', 150),
(4, 'Chennai', 'Hyderabad', '2025-01-15 11:00:00', '2025-01-15 13:00:00', 140),
(5, 'Bangalore', 'Goa', '2025-01-16 18:00:00', '2025-01-16 19:30:00', 120),
(1, 'Mumbai', 'Delhi', '2025-01-17 10:00:00', '2025-01-17 12:30:00', 180),
(2, 'Bangalore', 'Chennai', '2025-01-18 08:00:00', '2025-01-18 10:00:00', 170),
(3, 'Kolkata', 'Mumbai', '2025-01-19 16:00:00', '2025-01-19 18:00:00', 160),
(4, 'Hyderabad', 'Delhi', '2025-01-20 06:00:00', '2025-01-20 08:30:00', 150),
(5, 'Goa', 'Bangalore', '2025-01-21 22:00:00', '2025-01-21 23:30:00', 130);

-- Create the Bookings table
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    route_id INT,
    seats_booked INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('CONFIRMED','CANCELLED') NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (route_id) REFERENCES routes(route_id)
);

-- Insert sample data into Bookings table with updated future booking dates
INSERT INTO bookings (user_id, route_id, seats_booked, total_price, status, booking_date) VALUES
(1, 1, 2, 11000.00, 'CONFIRMED','2024-12-20 09:00:00'),
(2, 3, 1, 3500.00,'CONFIRMED','2024-12-21 11:30:00'),
(3, 4, 2,12000.00,'CONFIRMED','2024-12-22 14:00:00'),
(4, 5,1 ,3300.00,'CONFIRMED','2024-12-23 17:00:00'),
(5,2 ,3 ,10500.00,'CONFIRMED','2024-12-24 16:00:00'),
(1 ,6 ,2 ,11000.00,'CONFIRMED','2024-12-25 09:30:00'),
(2 ,7 ,1 ,4000.00,'CONFIRMED','2024-12-26 13:30:00'),
(3 ,8 ,2 ,7000.00,'CONFIRMED','2024-12-27 10:45:00'),
(4 ,9 ,1 ,4500.00,'CONFIRMED','2024-12-28 15:15:00'),
(5 ,10 ,1 ,3900.00,'CONFIRMED','2024-12-29 20:30:00');

-- Select all records from each table to verify
SELECT * FROM users;
SELECT * FROM flights;
SELECT * FROM routes;
SELECT * FROM bookings;
