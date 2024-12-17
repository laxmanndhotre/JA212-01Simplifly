import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';  // Import AuthService
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'SimplyFly';  // Your application name

  constructor(public authService: AuthService, private router: Router) {}  // Inject AuthService to access user role and authentication status

  // Method to check if the current route is the login page
  isLoginPage(): boolean {
    return this.router.url === '/login';
  }
}
