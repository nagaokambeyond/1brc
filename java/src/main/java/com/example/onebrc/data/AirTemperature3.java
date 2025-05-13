package com.example.onebrc.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AirTemperature3 {
    private int minimumTemperature;
    private int maximumTemperature;
    private int summaryTemperature;
    private int count;

    public AirTemperature3(int temperature) {
        minimumTemperature = temperature;
        maximumTemperature = temperature;
        summaryTemperature = temperature;
        count = 1;
    }

    public AirTemperature3 add(int temperature) {
        if (temperature < minimumTemperature) {
            minimumTemperature = temperature;
        }
        if (temperature > maximumTemperature) {
            maximumTemperature = temperature;
        }
        summaryTemperature += temperature;
        count += 1;

        return this;
    }

    public int getMinimumTemperature() {
        return minimumTemperature;
    }

    public int getMaximumTemperature() {
        return maximumTemperature;
    }

    public int getSummaryTemperature() {
        return summaryTemperature;
    }

    public int getCount() {
        return count;
    }

    public AirTemperature3 add(AirTemperature3 temperature) {
        if (temperature.getMinimumTemperature() < minimumTemperature) {
            minimumTemperature = temperature.getMinimumTemperature();
        }
        if (temperature.getMaximumTemperature() > maximumTemperature) {
            maximumTemperature = temperature.getMaximumTemperature();
        }
        summaryTemperature += temperature.getSummaryTemperature();
        count += temperature.getCount();

        return this;
    }

    @Override
    public String toString() {
        final var TEN = BigDecimal.valueOf(10);
        final var summary = BigDecimal.valueOf(summaryTemperature);
        final var count = BigDecimal.valueOf(this.count);
        final var average = summary.divide(count, 2, RoundingMode.CEILING).divide(TEN, 2, RoundingMode.CEILING);
        final var min = BigDecimal.valueOf(minimumTemperature).divide(TEN, 2, RoundingMode.CEILING);
        final var max = BigDecimal.valueOf(maximumTemperature).divide(TEN, 2, RoundingMode.CEILING);

        return String.format(
            "%.2f/%.2f/%.2f",
            min, average, max
        );
    }
}
