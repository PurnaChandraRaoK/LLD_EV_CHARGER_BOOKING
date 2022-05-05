package com.purnachandrak.evchargebooking.controllers;

import com.purnachandrak.evchargebooking.database.ChargersManager;
import com.purnachandrak.evchargebooking.database.BookingsManager;
import com.purnachandrak.evchargebooking.model.Charger;
import com.purnachandrak.evchargebooking.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChargersController {
  private ChargersManager chargersManager;
  private BookingsManager bookingsManager;

  public ChargersController(ChargersManager chargersManager, BookingsManager bookingsManager) {
    this.chargersManager = chargersManager;
    this.bookingsManager = bookingsManager;
  }

  @RequestMapping(value = "/register/charger", method = RequestMethod.POST)
  public ResponseEntity regiserCharger(final String chargerId, final String chargerName) {
    chargersManager.createCharger(new Charger(chargerId, chargerName));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/charger/location", method = RequestMethod.POST)
  public ResponseEntity updateChargerLocation(
      final String chargerId, final Double newX, final Double newY) {

    chargersManager.updateChargerLocation(chargerId, new Location(newX, newY));
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/charger/availability", method = RequestMethod.POST)
  public ResponseEntity updateChargerAvailability(final String chargerId, final Boolean newAvailability) {
    chargersManager.updateChargerAvailability(chargerId, newAvailability);
    return ResponseEntity.ok("");
  }

  @RequestMapping(value = "/update/charger/end/booking", method = RequestMethod.POST)
  public ResponseEntity endBooking(final String chargerId) {
    Double price = bookingsManager.endBooking(chargersManager.getCharger(chargerId));
    return ResponseEntity.ok("");
  }
}
