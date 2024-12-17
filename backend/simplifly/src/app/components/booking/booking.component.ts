import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  booking: Booking = new Booking(0, 0, 0, 0, 0, 'CONFIRMED', '');

  constructor(
    private bookingService: BookingService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Retrieve route and user ID from previous navigation state
    const routeId = this.route.snapshot.queryParams['routeId'];
    const userId = this.route.snapshot.queryParams['userId'];

    if (routeId && userId) {
      this.booking.routeId = +routeId;
      this.booking.userId = +userId;
    } else {
      alert('Invalid booking details. Redirecting...');
      this.router.navigate(['/passenger-dashboard']);
    }
  }

  onSubmit(): void {
    this.bookingService.createBooking(this.booking).subscribe({
      next: (response) => {
        alert('Booking Confirmed!');
        this.router.navigate(['/passenger-dashboard']);
      },
      error: (error) => {
        console.error('Booking failed:', error);
        alert('Failed to confirm booking. Please try again.');
      }
    });
  }
}
