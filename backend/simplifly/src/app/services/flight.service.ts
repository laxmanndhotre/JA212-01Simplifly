import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Flight } from '../models/flight.model';

@Injectable({
  providedIn: 'root',
})
export class FlightService {
  private apiUrl = 'http://localhost:8080/api/flights';

  constructor(private http: HttpClient) {}

  // Get all flights
  getAllFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(this.apiUrl);
  }

  // Get flight by ID
  getFlightById(flightId: number): Observable<Flight> {
    const url = `${this.apiUrl}/${flightId}`;
    return this.http.get<Flight>(url);
  }

  // Create a new flight
  createFlight(flight: Flight): Observable<Flight> {
    return this.http.post<Flight>(this.apiUrl, flight);
  }

  // Update an existing flight
  updateFlight(flightId: number, flight: Flight): Observable<Flight> {
    const url = `${this.apiUrl}/${flightId}`;
    return this.http.put<Flight>(url, flight);
  }

  // Delete a flight by ID
  deleteFlight(flightId: number): Observable<any> {
    const url = `${this.apiUrl}/${flightId}`;
    return this.http.delete(url);
  }
}
