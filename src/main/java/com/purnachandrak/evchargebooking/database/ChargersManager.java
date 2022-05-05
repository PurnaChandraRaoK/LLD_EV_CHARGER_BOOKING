package com.purnachandrak.evchargebooking.database;

import com.purnachandrak.evchargebooking.exceptions.ChargerAlreadyExistsException;
import com.purnachandrak.evchargebooking.exceptions.ChargerNotFoundException;
import com.purnachandrak.evchargebooking.model.Charger;
import com.purnachandrak.evchargebooking.model.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;

public class ChargersManager {

  Map<String, Charger> chargers = new HashMap<>();

  public void createCharger(@NonNull final Charger newCharger) {
    if (chargers.containsKey(newCharger.getId())) {
      throw new ChargerAlreadyExistsException();
    }

    chargers.put(newCharger.getId(), newCharger);
  }

  public Charger getCharger(@NonNull final String chargerId) {
    if (!chargers.containsKey(chargerId)) {
      throw new ChargerNotFoundException();
    }
    return chargers.get(chargerId);
  }

  public void updateChargerLocation(@NonNull final String chargerId, @NonNull final Location newLocation) {
    if (!chargers.containsKey(chargerId)) {
      throw new ChargerNotFoundException();
    }
    chargers.get(chargerId).setCurrentLocation(newLocation);
  }

  public void updateChargerAvailability(
      @NonNull final String chargerId, @NonNull final Boolean newAvailability) {
    if (!chargers.containsKey(chargerId)) {
      throw new ChargerNotFoundException();
    }
    chargers.get(chargerId).setIsAvailable(newAvailability);
  }

  public List<Charger> getChargers(@NonNull final Location fromPoint, @NonNull final Double distance) {
    List<Charger> result = new ArrayList<>();
    for (Charger charger : chargers.values()) {
      if (charger.getIsAvailable() && charger.getCurrentLocation().distance(fromPoint) <= distance) {
        result.add(charger);
      }
    }
    return result;
  }
}
