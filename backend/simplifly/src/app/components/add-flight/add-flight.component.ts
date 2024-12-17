import { Component } from '@angular/core';
import { FlightService } from '../../services/flight.service';
import { Flight } from '../../models/flight.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css']
})
export class AddFlightComponent {
  flight: Flight = new Flight(0, '', '', 0, 0, '', '', 0);

  constructor(private flightService: FlightService, private router: Router) {}

  onSubmit() {
    // Assuming userId is stored in localStorage or some other way
    const userId = 2;  // Example userId, replace with actual local storage value if needed
    this.flight.ownerId = userId;

    this.flightService.createFlight(this.flight).subscribe(
      (data) => {
        console.log('Flight added successfully', data);
        this.router.navigate(['/flight-owner-dashboard']);  // Redirect after successful submission
      },
      (error) => {
        console.error('Error adding flight', error);
      }
    );
  }
}
