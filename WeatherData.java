package com.mycompany.weatherproject;

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

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windspeed;
    }

    public double getWindDirection() {
        return winddirection;
    }

    @Override
    public String toString() {
        return String.format("Location: %s, Temperature: %.2f°C, Wind Speed: %.2f km/h, Wind Direction: %.2f°",
                location, temperature, windspeed, winddirection);
    }
}
