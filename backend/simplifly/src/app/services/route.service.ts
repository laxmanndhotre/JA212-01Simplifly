import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Route } from '../models/route.model';

@Injectable({
  providedIn: 'root',
})
export class RouteService {
  private apiUrl = 'http://localhost:8080/api/routes';

  constructor(private http: HttpClient) {}

  // Get all routes
  getAllRoutes(): Observable<Route[]> {
    return this.http.get<Route[]>(this.apiUrl);
  }

  // Get route by ID
  getRouteById(routeId: number): Observable<Route> {
    const url = `${this.apiUrl}/${routeId}`;
    return this.http.get<Route>(url);
  }

  // Create a new route
  createRoute(route: Route): Observable<Route> {
    return this.http.post<Route>(this.apiUrl, route);
  }

  // Update an existing route
  updateRoute(routeId: number, route: Route): Observable<Route> {
    const url = `${this.apiUrl}/${routeId}`;
    return this.http.put<Route>(url, route);
  }

  // Delete a route by ID
  deleteRoute(routeId: number): Observable<any> {
    const url = `${this.apiUrl}/${routeId}`;
    return this.http.delete(url);
  }
}
