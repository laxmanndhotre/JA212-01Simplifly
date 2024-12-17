import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  loginError: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    if (this.email && this.password) {
      this.authService.login(this.email, this.password).subscribe({
        next: (response) => {
          // Redirect to the appropriate dashboard based on role
          this.loginError = false; // Reset error on successful login
        },
        error: () => {
          // Handle login failure (e.g., show error message)
          this.loginError = true;
        }
      });
    }
  }
}
