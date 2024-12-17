export class Flight {
  flightId: number;
  flightName: string;
  flightNumber: string;
  totalSeats: number;
  fare: number;
  baggageInfo: string;
  ownerName: string;
  ownerId: number;

  constructor(
    flightId: number,
    flightName: string,
    flightNumber: string,
    totalSeats: number,
    fare: number,
    baggageInfo: string,
    ownerName: string,
    ownerId: number
  ) {
    this.flightId = flightId;
    this.flightName = flightName;
    this.flightNumber = flightNumber;
    this.totalSeats = totalSeats;
    this.fare = fare;
    this.baggageInfo = baggageInfo;
    this.ownerName = ownerName;
    this.ownerId = ownerId;
  }
}
