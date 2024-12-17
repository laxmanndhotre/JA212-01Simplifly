import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model'; // Import the User model
import { Flight } from '../../models/flight.model'; // Import the Flight model
import { Route } from '../../models/route.model'; // Import the Route model
import { Booking } from '../../models/booking.model'; // Import the Booking model
import { UserService } from '../../services/user.service';
import { FlightService } from '../../services/flight.service';
import { RouteService } from '../../services/route.service';
import { BookingService } from '../../services/booking.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  users: User[] = []; // Use the User model
  flights: Flight[] = []; // Use the Flight model
  routes: Route[] = []; // Use the Route model
  bookings: Booking[] = []; // Use the Booking model

  constructor(
    private userService: UserService,
    private flightService: FlightService,
    private routeService: RouteService,
    private bookingService: BookingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUsers();
    this.loadFlights();
    this.loadRoutes();
    this.loadBookings();
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe((data: User[]) => (this.users = data));
  }

  loadFlights() {
    this.flightService.getAllFlights().subscribe((data: Flight[]) => (this.flights = data));
  }

  loadRoutes() {
    this.routeService.getAllRoutes().subscribe((data: Route[]) => (this.routes = data));
  }

  loadBookings() {
    this.bookingService.getAllBookings().subscribe((data: Booking[]) => (this.bookings = data));
  }

  navigateToAddUser() {
    this.router.navigate(['/add-user']);
  }

  navigateToAddFlight() {
    this.router.navigate(['/add-flight']);
  }

  navigateToAddRoute() {
    this.router.navigate(['/add-route']);
  }

  navigateToAddBooking() {
    this.router.navigate(['/add-booking']);
  }

  updateUser(userId: number) {
    this.router.navigate(['/update-user', userId]);
  }

  deleteUser(userId: number) {
    this.userService.deleteUser(userId).subscribe(
      () => {
        this.loadUsers(); // Reload the users list after successful deletion
      },
      (error) => {
        console.error('Error deleting user:', error); // Log the error to console for debugging
      }
    );
  }
  

  updateFlight(flightId: number) {
    this.router.navigate(['/update-flight', flightId]);
  }

  deleteFlight(flightId: number) {
    this.flightService.deleteFlight(flightId).subscribe(() => this.loadFlights());
  }

  updateRoute(routeId: number) {
    this.router.navigate(['/update-route', routeId]);
  }

  deleteRoute(routeId: number) {
    this.routeService.deleteRoute(routeId).subscribe(() => this.loadRoutes());
  }

  deleteBooking(bookingId: number) {
    this.bookingService.deleteBooking(bookingId).subscribe(() => this.loadBookings());
  }
}
