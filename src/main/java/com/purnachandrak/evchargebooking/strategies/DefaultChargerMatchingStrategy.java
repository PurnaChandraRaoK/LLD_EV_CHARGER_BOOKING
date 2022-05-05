package com.purnachandrak.evchargebooking.strategies;

import com.purnachandrak.evchargebooking.model.Charger;
import com.purnachandrak.evchargebooking.model.Location;
import com.purnachandrak.evchargebooking.model.Rider;
import java.util.List;
import lombok.NonNull;

public class DefaultChargerMatchingStrategy implements ChargerMatchingStrategy {

  @Override
  public Charger matchChargerToRider(
      @NonNull final Rider rider,
      @NonNull final List<Charger> candidateChargers,
      @NonNull final Location fromPoint,
      @NonNull final Location toPoint) {
    if (candidateChargers.isEmpty()) {
      return null;
    }
    return candidateChargers.get(0);
  }
}
