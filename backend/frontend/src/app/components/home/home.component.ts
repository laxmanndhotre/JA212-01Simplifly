import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
 
  users: User[] = [];
  registerForm!: FormGroup; 
  isRegistering: boolean = false; 
  showRegisterForm: boolean = false;
  errorMessage: string = '';
  
  
    constructor(private userService: UserService,private fb: FormBuilder) {
      this.registerForm = this.fb.group({ 
          username: ['', Validators.required],
          email: ['', [Validators.required, Validators.email]],
          contactNumber: ['', Validators.required],
          role: ['', Validators.required],
          passwordHash: ['', Validators.required]
        });
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
  
    ngOnInit(): void {
      this.getAllUsers();
    }
  
  
    openRegisterForm() { // Renamed
      this.isRegistering = true;
    }
  
    closeRegisterForm() { // Renamed
      this.isRegistering = false;
      this.registerForm.reset();
    }
  
    onRegisterSubmit() {
      if (this.registerForm.valid) {
        const newUser: User = this.registerForm.value;
        console.log("User data to send:", newUser); // Debugging: Check the data
  
        this.userService.registerUser(newUser).subscribe({
          next: (registeredUser) => { // Use registeredUser
            console.log('User registered successfully:', registeredUser);
            this.users.push(registeredUser); // Add to local user list
            this.closeRegisterForm();
            this.getAllUsers();
          },
          error: (error: HttpErrorResponse) => {
            console.error('Error registering user:', error);
            if (error.error && error.error.message) {
              this.displayError(error.error.message);
            } else {
              this.displayError("An error occurred during registration.");
            }
          }
        });
      }
    }
    goBack() { this.showRegisterForm = false; }
    displayError(message: string) {
      this.errorMessage = message;
      setTimeout(() => {
        this.errorMessage = '';
      }, 3000);
    }
    toggleForm() {
      this.showRegisterForm = !this.showRegisterForm; 
    }
}
