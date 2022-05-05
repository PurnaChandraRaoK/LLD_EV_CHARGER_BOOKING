package com.purnachandrak.evchargebooking.model;

import static com.purnachandrak.evchargebooking.model.BookingStatus.FINISHED;
import static com.purnachandrak.evchargebooking.model.BookingStatus.IN_PROGRESS;

import lombok.NonNull;
import lombok.ToString;

enum BookingStatus {
  IN_PROGRESS,
  FINISHED
}

@ToString
public class Booking {
  private Rider rider;
  private Charger charger;
  private BookingStatus status;
  private Double price;
  private Location fromPoint;
  private Location toPoint;

  public Booking(
      @NonNull final Rider rider,
      @NonNull final Charger charger,
      @NonNull final Double price,
      @NonNull final Location fromPoint,
      @NonNull final Location toPoint) {
    this.rider = rider;
    this.charger = charger;
    this.price = price;
    this.fromPoint = fromPoint;
    this.toPoint = toPoint;
    this.status = IN_PROGRESS;
  }

  public void endBooking() {
    this.status = FINISHED;
  }
}
