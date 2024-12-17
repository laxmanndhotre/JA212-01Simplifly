import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightOwnerDashboardComponent } from './flight-owner-dashboard.component';

describe('FlightOwnerDashboardComponent', () => {
  let component: FlightOwnerDashboardComponent;
  let fixture: ComponentFixture<FlightOwnerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlightOwnerDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlightOwnerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
