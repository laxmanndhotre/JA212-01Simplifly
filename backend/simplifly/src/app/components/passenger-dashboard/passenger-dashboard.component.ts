import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../services/flight.service';
import { BookingService } from '../../services/booking.service';
import { RouteService } from '../../services/route.service';
import { Router } from '@angular/router';
import { Flight } from '../../models/flight.model';
import { Booking } from '../../models/booking.model';
import { Route } from '../../models/route.model';

@Component({
  selector: 'app-passenger-dashboard',
  templateUrl: './passenger-dashboard.component.html',
  styleUrls: ['./passenger-dashboard.component.css'],
})
export class PassengerDashboardComponent implements OnInit {
  flights: (Flight & { route?: Route })[] = [];
  bookings: Booking[] = [];

  constructor(
    private flightService: FlightService,
    private bookingService: BookingService,
    private routeService: RouteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadFlights();
    this.loadBookings();
  }

  loadFlights(): void {
    this.flightService.getAllFlights().subscribe((flights) => {
      this.routeService.getAllRoutes().subscribe((routes) => {
        this.flights = flights.map((flight) => ({
          ...flight,
          route: routes.find((route) => route.flightId === flight.flightId),
        }));
      });
    });
  }

  loadBookings(): void {
    const userId = 2; // Assuming logged-in user's ID is 2
    this.bookingService.getAllBookings().subscribe((bookings) => {
      this.bookings = bookings.filter((booking) => booking.userId === userId);
    });
  }

  redirectToBooking(flightId: number): void {
    this.router.navigate(['/booking', flightId]);
  }

  cancelBooking(bookingId: number): void {
    this.bookingService.deleteBooking(bookingId).subscribe(() => {
      this.bookings = this.bookings.filter((b) => b.bookingId !== bookingId);
    });
  }

  redirectToUpdateProfile(): void {
    this.router.navigate(['/update-user']);
  }

  logout(): void {
    this.router.navigate(['/login']);
  }
}
