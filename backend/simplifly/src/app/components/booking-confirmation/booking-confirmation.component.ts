import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { Flight } from '../../models/flight.model';

@Component({
  selector: 'app-booking-confirmation',
  templateUrl: './booking-confirmation.component.html',
  styleUrls: ['./booking-confirmation.component.css']
})
export class BookingConfirmationComponent {
  selectedFlight: Flight | null = null;

  constructor(private route: ActivatedRoute) { 
    this.route.data.subscribe(data => {
      this.selectedFlight = data['flight']; 
    });
  }
}