package com.example.onebrc.data;

public class AirTemperature2 {
    private int minimumTemperature;
    private int maximumTemperature;
    private int summaryTemperature;
    private int count;

    public AirTemperature2(int temperature) {
        minimumTemperature = temperature;
        maximumTemperature = temperature;
        summaryTemperature = temperature;
        count = 1;
    }

    public AirTemperature2 add(int temperature) {
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

    @Override
    public String toString() {
        final float average = (float) summaryTemperature / count / 10;
        final float min = (float) minimumTemperature / 10;
        final float max = (float) maximumTemperature / 10;
        return String.format(
            "%.2f/%.2f/%.2f",
            min, average, max
        );
    }
}
