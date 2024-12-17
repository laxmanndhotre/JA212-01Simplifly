import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { FlightOwnerDashboardComponent } from './components/flight-owner-dashboard/flight-owner-dashboard.component';
import { PassengerDashboardComponent } from './components/passenger-dashboard/passenger-dashboard.component';
import { AddFlightComponent } from './components/add-flight/add-flight.component';
import { AddRouteComponent } from './components/add-route/add-route.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { UpdateFlightComponent } from './components/update-flight/update-flight.component';
import { UpdateRouteComponent } from './components/update-route/update-route.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';
import { BookingComponent } from './components/booking/booking.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect to login by default

  // Login route
  { path: 'login', component: LoginComponent },

  // Admin routes
  { path: 'admin-dashboard', component: AdminDashboardComponent },

  // Flight Owner routes
  { path: 'flight-owner-dashboard', component: FlightOwnerDashboardComponent },
  { path: 'add-flight', component: AddFlightComponent },
  { path: 'add-route', component: AddRouteComponent },
  { path: 'update-flight/:id', component: UpdateFlightComponent },
  { path: 'update-route/:id', component: UpdateRouteComponent },

  // Passenger routes
  { path: 'passenger-dashboard', component: PassengerDashboardComponent },
  { path: 'booking', component: BookingComponent },

  // Admin User Management
  { path: 'add-user', component: AddUserComponent },
  { path: 'update-user/:id', component: UpdateUserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
