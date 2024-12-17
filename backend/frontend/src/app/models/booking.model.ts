export class Booking {
  bookingId: number;
  userId: number;
  routeId: number;
  seatsBooked: number;
  totalPrice: number;
  status: 'CONFIRMED' | 'CANCELLED';
  bookingDate: string; // Assuming date is stored as string, can be nullable if needed

  constructor(
    bookingId: number,
    userId: number,
    routeId: number,
    seatsBooked: number,
    totalPrice: number,
    status: 'CONFIRMED' | 'CANCELLED',
    bookingDate: string // If needed, you can set it to null by default
  ) {
    this.bookingId = bookingId;
    this.userId = userId;
    this.routeId = routeId;
    this.seatsBooked = seatsBooked;
    this.totalPrice = totalPrice;
    this.status = status;
    this.bookingDate = bookingDate;
  }
}
