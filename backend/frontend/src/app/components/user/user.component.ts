import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user.model';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: User[] = [];
  errorMessage: string = '';
  registerForm!: FormGroup; 
  isRegistering: boolean = false; 


  constructor(private userService: UserService,private fb: FormBuilder) {
    this.registerForm = this.fb.group({ 
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        contactNumber: ['', Validators.required],
        role: ['', Validators.required],
        passwordHash: ['', Validators.required]
      });
  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users) => this.users = users,
      error: (error) => {
        console.error('Error fetching users:', error);
        this.displayError("Failed to load users.");
      }
    });
  }

  confirmDelete(user: User) {
    if (confirm(`Are you sure you want to delete ${user.username}?`)) {
      this.deleteUser(user); // Call the correct delete method
    }
  }

  deleteUser(user: User) { 
    if (user && user.userId) {
      this.userService.deleteUser(user.userId).subscribe({
        next: () => {
          console.log('User deleted successfully from database');
          this.users = this.users.filter(u => u.userId !== user.userId); //
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error deleting user from database:', error);
          if (error.error && error.error.message) {
            this.displayError(error.error.message);
          } else {
            this.displayError("An error occurred while deleting the user.");
          }
        }
      });
    } else {
      console.error("User or user ID is undefined.");
      this.displayError("Invalid user selected for deletion.");
    }
  }

  displayError(message: string) {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = '';
    }, 3000);
  }

}