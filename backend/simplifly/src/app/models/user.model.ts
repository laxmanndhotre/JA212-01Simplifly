export class User {
  userId: number;
  username: string;
  email: string;
  role: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN';
  password?: string | null;
  createdAt?: string | null; // Handle createdAt as a string, or null if absent

  constructor(
    userId: number,
    username: string,
    email: string,
    role: 'PASSENGER' | 'FLIGHT_OWNER' | 'ADMIN',
    password?: string,
    createdAt?: string // Optional string type for createdAt
  ) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.role = role;
    this.password = password || null;
    this.createdAt = createdAt || null; // Default to null if no value is provided
  }
}
