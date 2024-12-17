import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http'; // For HTTP requests
import { FormsModule } from '@angular/forms'; // For form handling
import { ReactiveFormsModule } from '@angular/forms'; // For reactive forms

// Components
import { LoginComponent } from './components/login/login.component';
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

// Services
import { AuthService } from './services/auth.service';
import { BookingService } from './services/booking.service';
import { FlightService } from './services/flight.service';
import { RouteService } from './services/route.service';
import { UserService } from './services/user.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminDashboardComponent,
    FlightOwnerDashboardComponent,
    PassengerDashboardComponent,
    AddFlightComponent,
    AddRouteComponent,
    AddUserComponent,
    UpdateFlightComponent,
    UpdateRouteComponent,
    UpdateUserComponent,
    BookingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,   // HTTP requests for backend API interaction
    FormsModule,        // Template-driven forms
    ReactiveFormsModule // Reactive forms for handling complex forms
  ],
  providers: [
    AuthService,        // Auth service for login and session management
    BookingService,     // Service to manage bookings
    FlightService,      // Service for flight-related actions
    RouteService,       // Service for route-related actions
    UserService,        // Service for user management (add, update, etc.)
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
