import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';

export interface LoginResponse {
  user_id: number;
  username: string;
  email: string;
  role: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN';
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApiUrl = 'http://localhost:8080/api/auth'; // Backend URL for authentication
  private currentRole: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN' | null = null;
  private currentUserId: number | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  // Login method: authenticate user and store user data (id, role)
  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.authApiUrl}/login`, { email, password }).pipe(
      tap((response) => {
        if (response) {
          this.setRole(response.role);
          this.setUserId(response.user_id);
          // Navigate to the correct dashboard based on role
          this.navigateToDashboard(response.role);
        }
      })
    );
  }

  // Set the role of the user in the service and localStorage
  setRole(role: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN'): void {
    this.currentRole = role;
    localStorage.setItem('userRole', role);
  }

  // Get the current user's role (from service or localStorage)
  getCurrentRole(): 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN' | null {
    return this.currentRole || (localStorage.getItem('userRole') as 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN' | null);
  }

  // Set the user ID in the service and localStorage
  setUserId(userId: number): void {
    this.currentUserId = userId;
    localStorage.setItem('userId', userId.toString());
  }

  // Get the current user's ID (from service or localStorage)
  getUserId(): number | null {
    if (this.currentUserId !== null) return this.currentUserId;
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null;
  }

  // Logout functionality: clear all user-related data
  logout(): void {
    this.currentRole = null;
    this.currentUserId = null;
    localStorage.removeItem('userRole');
    localStorage.removeItem('userId');
    this.router.navigate(['/login']); // Navigate to login page after logout
  }

  // Helper function to navigate user based on their role
  private navigateToDashboard(role: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN'): void {
    switch (role) {
      case 'PASSENGER':
        this.router.navigate(['/passenger-dashboard']);
        break;
      case 'FLIGHT_OWNER':
        this.router.navigate(['/flight-owner-dashboard']);
        break;
      case 'ADMIN':
        this.router.navigate(['/admin-dashboard']);
        break;
      default:
        this.router.navigate(['/login']);
    }
  }

  // Optionally, if you need other API endpoints, add them here
  // Example: Get user details
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('http://localhost:8080/api/users');
  }
}
