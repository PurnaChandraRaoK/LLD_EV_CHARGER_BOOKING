package com.purnachandrak.evchargebooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Charger {
  String id;
  String chargerName;

  @Setter Booking currentBooking;
  @Setter Location currentLocation;
  @Setter Boolean isAvailable;

  public Charger(String id, String chargerName) {
    this.id = id;
    this.chargerName = chargerName;
    this.isAvailable = true;
  }

  @Override
  public String toString() {
    return "Charger{" +
        "id='" + id + '\'' +
        ", chargerName='" + chargerName + '\'' +
        ", currentLocation=" + currentLocation +
        ", isAvailable=" + isAvailable +
        '}';
  }
}
