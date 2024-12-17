import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  user: User = new User(0, '', '', 'PASSENGER', '', '');

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userId = +this.route.snapshot.params['id'];

    if (userId) {
      this.userService.getUserById(userId).subscribe({
        next: (data) => {
          this.user = data;
        },
        error: (err) => {
          console.error('Error fetching user:', err);
          alert('Unable to load user data. Redirecting...');
          this.router.navigate(['/passenger-dashboard']);
        }
      });
    } else {
      alert('Invalid user ID. Redirecting...');
      this.router.navigate(['/passenger-dashboard']);
    }
  }

  onSubmit(): void {
    this.userService.updateUser(this.user.userId, this.user).subscribe({
      next: () => {
        alert('Profile updated successfully!');
        this.router.navigate(['/passenger-dashboard']);
      },
      error: (err) => {
        console.error('Error updating user:', err);
        alert('Failed to update profile. Please try again.');
      }
    });
  }
}
