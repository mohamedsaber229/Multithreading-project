package com.weatherapp;

public class WeatherData {
    private final String location;
    private final double temperature;
    private final double windspeed;
    private final double winddirection;

    public WeatherData(String location, double temperature, double windspeed, double winddirection) {
        this.location = location;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.winddirection = winddirection;
    }

    @Override
    public String toString() {
        return String.format("Weather in %s: %.1f°C, Wind Speed: %.1f km/h, Wind Direction: %.1f°",
                location, temperature, windspeed, winddirection);
    }
}
