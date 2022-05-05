package com.purnachandrak.evchargebooking.strategies;

import com.purnachandrak.evchargebooking.model.Location;

public interface PricingStrategy {
  Double findPrice(Location fromPoint, Location toPoint);
}
