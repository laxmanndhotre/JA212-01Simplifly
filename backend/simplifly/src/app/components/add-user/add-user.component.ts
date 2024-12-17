import { Component } from '@angular/core';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent {
  user: User = new User(0, '', '', 'PASSENGER', '');

  constructor(private userService: UserService, private router: Router) {}

  onSubmit() {
    this.userService.createUser(this.user).subscribe(
      (response) => {
        console.log('User added successfully:', response);
        this.router.navigate(['/admin-dashboard']); // Redirect to admin dashboard
      },
      (error) => {
        console.error('Error adding user:', error);
      }
    );
  }
}
