import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../services/flight.service';
import { Flight } from '../../models/flight.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-flight',
  templateUrl: './update-flight.component.html',
  styleUrls: ['./update-flight.component.css']
})
export class UpdateFlightComponent implements OnInit {
  flightId!: number;
  flight: Flight = new Flight(0, '', '', 0, 0, '', '', 0);

  constructor(
    private flightService: FlightService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    // Safely get the flightId from route params
    const flightIdParam = this.route.snapshot.paramMap.get('id');
    if (flightIdParam) {
      this.flightId = +flightIdParam;  // Ensure it's converted to a number
      this.flightService.getFlightById(this.flightId).subscribe((data) => {
        this.flight = data;
      });
    } else {
      console.error('No flightId found in route parameters');
      // Handle error: maybe redirect or show a message
    }
  }

  onSubmit() {
    const userId = 2;  // Example userId, replace with actual local storage value if needed
    this.flight.ownerId = userId;

    this.flightService.updateFlight(this.flightId, this.flight).subscribe(
      (data) => {
        console.log('Flight updated successfully', data);
        this.router.navigate(['/flight-owner-dashboard']);  // Redirect after successful update
      },
      (error) => {
        console.error('Error updating flight', error);
      }
    );
  }
}
