import { Component, OnInit } from '@angular/core';
import { Flight } from '../../models/flight.model';
import { Route } from '../../models/route.model';
import { FlightService } from '../../services/flight.service';
import { RouteService } from '../../services/route.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-flight-owner-dashboard',
  templateUrl: './flight-owner-dashboard.component.html',
  styleUrls: ['./flight-owner-dashboard.component.css']
})
export class FlightOwnerDashboardComponent implements OnInit {
  flights: Flight[] = [];
  routes: Route[] = [];

  constructor(
    private flightService: FlightService,
    private routeService: RouteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadFlights();
    this.loadRoutes();
  }

  loadFlights(): void {
    this.flightService.getAllFlights().subscribe(
      (data) => {
        this.flights = data;
      },
      (error) => {
        console.error('Error fetching flights:', error);
      }
    );
  }

  loadRoutes(): void {
    this.routeService.getAllRoutes().subscribe(
      (data) => {
        this.routes = data;
      },
      (error) => {
        console.error('Error fetching routes:', error);
      }
    );
  }

  navigateToAddFlight(): void {
    this.router.navigate(['/add-flight']);
  }

  navigateToAddRoute(): void {
    this.router.navigate(['/add-route']);
  }

  updateFlight(flightId: number): void {
    this.router.navigate(['/update-flight', flightId]);
  }

  deleteFlight(flightId: number): void {
    this.flightService.deleteFlight(flightId).subscribe(
      () => {
        this.flights = this.flights.filter(flight => flight.flightId !== flightId);
        alert('Flight deleted successfully');
      },
      (error) => {
        console.error('Error deleting flight:', error);
      }
    );
  }

  updateRoute(routeId: number): void {
    this.router.navigate(['/update-route', routeId]);
  }

  deleteRoute(routeId: number): void {
    this.routeService.deleteRoute(routeId).subscribe(
      () => {
        this.routes = this.routes.filter(route => route.routeId !== routeId);
        alert('Route deleted successfully');
      },
      (error) => {
        console.error('Error deleting route:', error);
      }
    );
  }
}
