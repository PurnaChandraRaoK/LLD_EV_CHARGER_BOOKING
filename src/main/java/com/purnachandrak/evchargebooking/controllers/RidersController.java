package com.purnachandrak.evchargebooking.controllers;

import com.purnachandrak.evchargebooking.database.RidersManager;
import com.purnachandrak.evchargebooking.database.BookingsManager;
import com.purnachandrak.evchargebooking.model.Location;
import com.purnachandrak.evchargebooking.model.Rider;
import com.purnachandrak.evchargebooking.model.Booking;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RidersController {
  private RidersManager ridersManager;
  private BookingsManager bookingsManager;

  public RidersController(RidersManager ridersManager, BookingsManager bookingsManager) {
    this.ridersManager = ridersManager;
    this.bookingsManager = bookingsManager;
  }

  @RequestMapping(value = "/register/rider", method = RequestMethod.POST)
  public ResponseEntity registerRider(final String riderId, final String riderName) {
    ridersManager.createRider(new Rider(riderId, riderName));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/book", method = RequestMethod.POST)
  public ResponseEntity book(
      final String riderId,
      final Double sourceX,
      final Double sourceY,
      final Double destX,
      final Double destY) {

    bookingsManager.createBooking(
        ridersManager.getRider(riderId),
        new Location(sourceX, sourceY),
        new Location(destX, destY));

    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/getMyBookings", method = RequestMethod.GET)
  public ResponseEntity fetchHistory(final String riderId) {
    List<Booking> bookings = bookingsManager.bookingHistory(ridersManager.getRider(riderId));
    return ResponseEntity.ok(bookings);
  }
}
