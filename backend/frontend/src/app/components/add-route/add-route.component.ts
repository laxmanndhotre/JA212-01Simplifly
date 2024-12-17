import { Component } from '@angular/core';
import { Route } from '../../models/route.model';
import { RouteService } from '../../services/route.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-route',
  templateUrl: './add-route.component.html',
  styleUrls: ['./add-route.component.css']
})
export class AddRouteComponent {
  route: Route = new Route(0, 0, '', '', '', '', 0);

  constructor(private routeService: RouteService, private router: Router) {}

  onSubmit(): void {
    this.routeService.createRoute(this.route).subscribe(
      (response) => {
        console.log('Route added successfully:', response);
        this.router.navigate(['/flight-owner-dashboard']);  // Redirect to dashboard
      },
      (error) => {
        console.error('Error adding route:', error);
      }
    );
  }
}
