import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking } from '../models/booking.model';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) {}

  // Get all bookings
  getAllBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.apiUrl);
  }

  // Get booking by ID
  getBookingById(bookingId: number): Observable<Booking> {
    const url = `${this.apiUrl}/${bookingId}`;
    return this.http.get<Booking>(url);
  }

  // Create a new booking
  createBooking(booking: Booking): Observable<Booking> {
    return this.http.post<Booking>(this.apiUrl, booking);
  }

  // Update an existing booking
  updateBooking(bookingId: number, booking: Booking): Observable<Booking> {
    const url = `${this.apiUrl}/${bookingId}`;
    return this.http.put<Booking>(url, booking);
  }

  // Delete a booking by ID
  deleteBooking(bookingId: number): Observable<any> {
    const url = `${this.apiUrl}/${bookingId}`;
    return this.http.delete(url);
  }
}
