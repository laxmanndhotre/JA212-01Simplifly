import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Flight } from 'src/app/models/flight.model';
import { User } from 'src/app/models/user.model';
import { FlightService } from 'src/app/services/flight.service';
import { UserService } from 'src/app/services/user.service';
import { RouteService } from 'src/app/services/route.service';
import { Route } from 'src/app/models/route.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-userdashboard',
  templateUrl: './userdashboard.component.html',
  styleUrls: ['./userdashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  currentUser: User | null = null;
  errorMessage: string = '';
  flights: Flight[] = [];
  routes: Route[] = []; // Store the fetched routes
  selectedFlight: Flight | null = null;
  selectedRoute: Route | null = null; // Store the selected route details
  showFlightDetails: boolean = false; // Define showFlightDetails variable

  constructor(
    private router: Router,
    private userService: UserService,
    private flightService: FlightService,
    private routeService: RouteService,
    private datePipe: DatePipe,
    private dialog: MatDialog
  ) {
    const now = new Date();
    console.log(this.datePipe.transform(now, 'yyyy-MM-dd'));
  }

  ngOnInit(): void {
    this.loadCurrentUser();
    this.loadRoutes();
    this.loadFlights(); // Load all flights initially
  }

  loadCurrentUser() {
    const userId = this.userService.getCurrentUserId();

    if (userId) {
      this.userService.getUserById(userId).subscribe({
        next: (user) => {
          this.currentUser = user;
        },
        error: (error) => {
          console.error('Error fetching user:', error);
          this.displayError("Failed to load user data.");
        }
      });
    } else {
      this.displayError("User ID not available.");
    }
  }

  loadRoutes() {
    this.routeService.getAllRoutes().subscribe({
      next: (routes) => {
        this.routes = routes;
        console.log("Fetched routes:", this.routes); // Log the routes to the console
      },
      error: (error) => {
        this.errorMessage = 'Error fetching routes';
        console.error('Error fetching routes:', error);
      }
    });
  }

  loadFlights() {
    this.flightService.getAllFlights().subscribe({
      next: (flights) => {
        this.flights = flights;
        console.log("Fetched flights:", this.flights); // Log the flights to the console
      },
      error: (error) => {
        this.errorMessage = 'Error fetching flights';
        console.error('Error fetching flights:', error);
      }
    });
  }

  showFlights() {
    const dialogRef = this.dialog.open(AvailableFlightsDialogComponent, {
      width: '800px',
      data: { routes: this.routes }
    });

    dialogRef.afterClosed().subscribe((selectedRoute: Route | null) => {
      if (selectedRoute) {
        this.selectFlight(selectedRoute);
      }
    });
  }

  selectFlight(route: Route) {
    this.selectedRoute = route; // Store the selected route
    this.flightService.getFlightById(route.flightId).subscribe((flight) => {
      this.selectedFlight = flight;
      this.showFlightDetails = true; // Set showFlightDetails to true when a flight is selected
    });
  }

  navigateToBooking() {
    if (this.selectedFlight) {
      this.router.navigate(['/booking-confirmation'], {
        state: { flight: this.selectedFlight }
      });
    }
  }

  displayError(message: string) {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = '';
    }, 3000);
  }

  getFlightName(flight_id: number): string {
    const flight = this.flights.find(f => f.flightId === flight_id);
    return flight ? flight.flightName : 'Unknown';
  }

  getFlightNumber(flight_id: number): string {
    const flight = this.flights.find(f => f.flightId === flight_id);
    return flight ? flight.flightNumber : 'Unknown';
  }
}
