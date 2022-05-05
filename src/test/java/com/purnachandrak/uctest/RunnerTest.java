package com.purnachandrak.uctest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.purnachandrak.evchargebooking.controllers.ChargersController;
import com.purnachandrak.evchargebooking.controllers.RidersController;
import com.purnachandrak.evchargebooking.database.BookingsManager;
import com.purnachandrak.evchargebooking.database.ChargersManager;
import com.purnachandrak.evchargebooking.database.RidersManager;
import com.purnachandrak.evchargebooking.exceptions.ChargerAlreadyExistsException;
import com.purnachandrak.evchargebooking.exceptions.ChargerNotFoundException;
import com.purnachandrak.evchargebooking.exceptions.NoChargersAvailableException;
import com.purnachandrak.evchargebooking.exceptions.RiderAlreadyExistsException;
import com.purnachandrak.evchargebooking.exceptions.RiderNotFoundException;
import com.purnachandrak.evchargebooking.strategies.ChargerMatchingStrategy;
import com.purnachandrak.evchargebooking.strategies.DefaultChargerMatchingStrategy;
import com.purnachandrak.evchargebooking.strategies.DefaultPricingStrategy;
import com.purnachandrak.evchargebooking.strategies.PricingStrategy;

public class RunnerTest {
  ChargersController chargersController;
  RidersController ridersController;

  @BeforeEach
  void setUp() {
    ChargersManager chargersManager = new ChargersManager();
    RidersManager ridersManager = new RidersManager();

    ChargerMatchingStrategy chargerMatchingStrategy = new DefaultChargerMatchingStrategy();
    PricingStrategy pricingStrategy = new DefaultPricingStrategy();

    BookingsManager bookingsManager = new BookingsManager(chargersManager, ridersManager, chargerMatchingStrategy, pricingStrategy);

    chargersController = new ChargersController(chargersManager, bookingsManager);
    ridersController = new RidersController(ridersManager, bookingsManager);
  }

  @Test
  void testEVChargeBookingTestApplicationFlow() {

    String r1 = "r1";
    ridersController.registerRider(r1, "ud");
    String r2 = "r2";
    ridersController.registerRider(r2, "du");
    String r3 = "r3";
    ridersController.registerRider(r3, "rider3");
    String r4 = "r4";
    ridersController.registerRider(r4, "rider4");

    String c1 = "c1";
    chargersController.regiserCharger(c1, "charger1");
    String c2 = "c2";
    chargersController.regiserCharger(c2, "charger2");
    String c3 = "c3";
    chargersController.regiserCharger(c3, "charger3");
    String c4 = "c4";
    chargersController.regiserCharger(c4, "charger4");
    String c5 = "c5";
    chargersController.regiserCharger(c5, "charger5");

    chargersController.updateChargerLocation(c1, 1.0, 1.0);
    chargersController.updateChargerLocation(c2, 2.0, 2.0); //na
    chargersController.updateChargerLocation(c3, 100.0, 100.0);
    chargersController.updateChargerLocation(c4, 110.0, 110.0); //na
    chargersController.updateChargerLocation(c5, 4.0, 4.0);

    chargersController.updateChargerAvailability(c2, false);
    chargersController.updateChargerAvailability(c4, false);

    ridersController.book(r1, 0.0, 0.0, 500.0, 500.0);
    ridersController.book(r2, 0.0, 0.0, 500.0, 500.0);

    System.out.println("\n### Printing current bookings for r1 and r2");
    System.out.println(ridersController.fetchHistory(r1).getBody());
    System.out.println(ridersController.fetchHistory(r2).getBody());

    chargersController.updateChargerLocation(c5, 50.0, 50.0);

    System.out.println("\n### Printing current bookings for r1 and r2");
    System.out.println(ridersController.fetchHistory(r1).getBody());
    System.out.println(ridersController.fetchHistory(r2).getBody());

    chargersController.endBooking(c5);

    System.out.println("\n### Printing current bookings for r1 and r2");
    System.out.println(ridersController.fetchHistory(r1).getBody());
    System.out.println(ridersController.fetchHistory(r2).getBody());


    assertThrows(NoChargersAvailableException.class, () -> {
      ridersController.book(r3, 0.0, 0.0, 500.0, 500.0);
    });

    ridersController.book(r4, 48.0, 48.0, 500.0, 500.0);
    System.out.println("\n### Printing current bookings for r1, r2 and r4");
    System.out.println(ridersController.fetchHistory(r1).getBody());
    System.out.println(ridersController.fetchHistory(r2).getBody());
    System.out.println(ridersController.fetchHistory(r4).getBody());

    assertThrows(RiderNotFoundException.class, () -> {
      ridersController.book("abcd", 0.0, 0.0, 500.0, 500.0);
    });

    assertThrows(RiderAlreadyExistsException.class, () -> {
      ridersController.registerRider("r1", "shjgf");
    });

    assertThrows(ChargerAlreadyExistsException.class, () -> {
      chargersController.regiserCharger("c1", "skjhsfkj");
    });

    assertThrows(ChargerNotFoundException.class, () -> {
      chargersController.updateChargerLocation("shss", 110.0, 110.0);
    });

    assertThrows(ChargerNotFoundException.class, () -> {
      chargersController.updateChargerAvailability("shss", false);
    });
  }
}
