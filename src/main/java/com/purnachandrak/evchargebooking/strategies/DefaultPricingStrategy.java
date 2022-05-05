package com.purnachandrak.evchargebooking.strategies;

import com.purnachandrak.evchargebooking.model.Location;

public class DefaultPricingStrategy implements PricingStrategy {

  public static final Double PER_HOUR_RATE = 10.0;

  @Override
  public Double findPrice(Location fromPoint, Location toPoint) {
    return fromPoint.distance(toPoint) * PER_HOUR_RATE;
  }
}
