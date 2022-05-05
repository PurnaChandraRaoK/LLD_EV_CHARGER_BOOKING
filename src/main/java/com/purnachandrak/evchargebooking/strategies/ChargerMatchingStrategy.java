package com.purnachandrak.evchargebooking.strategies;

import com.purnachandrak.evchargebooking.model.Charger;
import com.purnachandrak.evchargebooking.model.Location;
import com.purnachandrak.evchargebooking.model.Rider;
import java.util.List;

public interface ChargerMatchingStrategy {

  Charger matchChargerToRider(Rider rider, List<Charger> candidateChargers, Location fromPoint, Location toPoint);
}
