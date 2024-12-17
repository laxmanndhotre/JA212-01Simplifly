import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Route } from '../../models/route.model';
import { RouteService } from '../../services/route.service';

@Component({
  selector: 'app-update-route',
  templateUrl: './update-route.component.html',
  styleUrls: ['./update-route.component.css']
})
export class UpdateRouteComponent implements OnInit {
  routeId: number = 0;
  route: Route = new Route(0, 0, '', '', '', '', 0);

  constructor(
    private routeService: RouteService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.routeId = this.activatedRoute.snapshot.params['id'];
    this.getRouteDetails();
  }

  getRouteDetails(): void {
    this.routeService.getRouteById(this.routeId).subscribe(
      (data) => {
        this.route = data;
      },
      (error) => {
        console.error('Error fetching route details:', error);
      }
    );
  }

  onSubmit(): void {
    this.routeService.updateRoute(this.routeId, this.route).subscribe(
      (response) => {
        console.log('Route updated successfully:', response);
        this.router.navigate(['/flight-owner-dashboard']);  // Redirect to dashboard
      },
      (error) => {
        console.error('Error updating route:', error);
      }
    );
  }
}
