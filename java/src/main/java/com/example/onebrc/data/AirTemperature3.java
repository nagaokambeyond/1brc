package com.example.onebrc.data;

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
        final float average = (float) summaryTemperature / count / 10;
        final float min = (float) minimumTemperature / 10;
        final float max = (float) maximumTemperature / 10;
        return String.format(
            "%.2f/%.2f/%.2f",
            min, average, max
        );
    }
}
