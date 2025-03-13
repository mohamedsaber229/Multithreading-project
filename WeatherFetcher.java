package com.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherFetcher implements Callable<WeatherData> {
    private final String location;

    private static final Map<String, double[]> CITY_COORDINATES = Map.of(
        "New York", new double[]{40.7128, -74.0060},
        "London", new double[]{51.5074, -0.1278},
        "Tokyo", new double[]{35.6895, 139.6917},
        "Cairo", new double[]{30.0444, 31.2357},
        "Paris", new double[]{48.8566, 2.3522}
    );

    public WeatherFetcher(String location) {
        this.location = location;
    }

    @Override
    public WeatherData call() throws Exception {
        double[] coordinates = CITY_COORDINATES.get(location);
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates not found for location: " + location);
        }

        String apiUrl = String.format(
            "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&current_weather=true",
            coordinates[0], coordinates[1]
        );

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed to fetch weather data for " + location);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String response = reader.lines().reduce("", String::concat);
            JsonObject weatherJson = JsonParser.parseString(response).getAsJsonObject()
                    .getAsJsonObject("current_weather");

            if (weatherJson == null) {
                throw new IllegalStateException("Missing 'current_weather' in API response");
            }

            double temperature = weatherJson.get("temperature").getAsDouble();
            double windspeed = weatherJson.get("windspeed").getAsDouble();
            double winddirection = weatherJson.get("winddirection").getAsDouble();

            return new WeatherData(location, temperature, windspeed, winddirection);
        }
    }
}
