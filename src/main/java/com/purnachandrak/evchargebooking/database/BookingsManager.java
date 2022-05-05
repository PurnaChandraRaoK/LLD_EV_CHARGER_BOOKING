package com.purnachandrak.evchargebooking.database;

import com.purnachandrak.evchargebooking.exceptions.NoChargersAvailableException;
import com.purnachandrak.evchargebooking.exceptions.BookingNotFoundException;
import com.purnachandrak.evchargebooking.model.Charger;
import com.purnachandrak.evchargebooking.model.Location;
import com.purnachandrak.evchargebooking.model.Rider;
import com.purnachandrak.evchargebooking.model.Booking;
import com.purnachandrak.evchargebooking.strategies.ChargerMatchingStrategy;
import com.purnachandrak.evchargebooking.strategies.PricingStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;

public class BookingsManager {

  public static final Double MAX_ALLOWED_BOOKING_MATCHING_DISTANCE = 10.0;
  private Map<String, List<Booking>> bookings = new HashMap<>();

  private ChargersManager chargersManager;
  private RidersManager ridersManager;
  private ChargerMatchingStrategy chargerMatchingStrategy;
  private PricingStrategy pricingStrategy;

  public BookingsManager(
      ChargersManager chargersManager,
      RidersManager ridersManager,
      ChargerMatchingStrategy chargerMatchingStrategy,
      PricingStrategy pricingStrategy) {
    this.chargersManager = chargersManager;
    this.ridersManager = ridersManager;
    this.chargerMatchingStrategy = chargerMatchingStrategy;
    this.pricingStrategy = pricingStrategy;
  }

  public void createBooking(
      @NonNull final Rider rider,
      @NonNull final Location fromPoint,
      @NonNull final Location toPoint) {
    final List<Charger> closeByChargers =
        chargersManager.getChargers(fromPoint, MAX_ALLOWED_BOOKING_MATCHING_DISTANCE);
    final List<Charger> closeByAvailableChargers =
        closeByChargers.stream()
            .filter(charger -> charger.getCurrentBooking() == null)
            .collect(Collectors.toList());

    final Charger selectedCharger =
        chargerMatchingStrategy.matchChargerToRider(rider, closeByAvailableChargers, fromPoint, toPoint);
    if (selectedCharger == null) {
      throw new NoChargersAvailableException();
    }

    final Double price = pricingStrategy.findPrice(fromPoint, toPoint);
    final Booking newBooking = new Booking(rider, selectedCharger, price, fromPoint, toPoint);
    if (!bookings.containsKey(rider.getId())) {
      bookings.put(rider.getId(), new ArrayList<>());
    }
    bookings.get(rider.getId()).add(newBooking);
    selectedCharger.setCurrentBooking(newBooking);
  }

  public List<Booking> bookingHistory(@NonNull final Rider rider) {
    return bookings.get(rider.getId());
  }

  public Double endBooking(@NonNull final Charger charger) {
    if (charger.getCurrentBooking() == null) {
      throw new BookingNotFoundException();
    }
    Double price = charger.getCurrentBooking().getPrice();
    charger.getCurrentBooking().endBooking();
    charger.setCurrentBooking(null);
    return price;
  }
}
