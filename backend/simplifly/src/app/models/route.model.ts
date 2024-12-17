export class Route {
  routeId: number;
  flightId: number;
  origin: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  availableSeats: number;

  constructor(
    routeId: number,
    flightId: number,
    origin: string,
    destination: string,
    departureTime: string,
    arrivalTime: string,
    availableSeats: number
  ) {
    this.routeId = routeId;
    this.flightId = flightId;
    this.origin = origin;
    this.destination = destination;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
    this.availableSeats = availableSeats;
  }
}
