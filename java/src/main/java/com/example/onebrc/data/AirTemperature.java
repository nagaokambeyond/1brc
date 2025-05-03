package com.example.onebrc.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AirTemperature {
    private BigDecimal minimumTemperature;
    private BigDecimal maximumTemperature;
    private BigDecimal summaryTemperature;
    private int count;

    public AirTemperature(BigDecimal temperature) {
        minimumTemperature = temperature;
        maximumTemperature = temperature;
        summaryTemperature = temperature;
        count = 1;
    }

    public AirTemperature add(BigDecimal temperature) {
        if (temperature.compareTo(minimumTemperature) < 0) {
            minimumTemperature = temperature;
        }
        if (temperature.compareTo(maximumTemperature) > 0) {
            maximumTemperature = temperature;
        }
        summaryTemperature = summaryTemperature.add(temperature);
        count += 1;

        return this;
    }

    @Override
    public String toString() {
        final var average = summaryTemperature.divide(BigDecimal.valueOf(count), 2, RoundingMode.CEILING);
        return String.format(
            "%f/%f/%f",
            minimumTemperature, average, maximumTemperature
        );
    }
}
