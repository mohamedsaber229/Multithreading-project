package com.mycompany.weatherproject;

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

    private static final Map<String, double[]> CITY_COORDINATES = Map.ofEntries(
        Map.entry("New York", new double[]{40.7128, -74.0060}),
        Map.entry("London", new double[]{51.5074, -0.1278}),
        Map.entry("Tokyo", new double[]{35.6895, 139.6917}),
        Map.entry("Sydney", new double[]{-33.8688, 151.2093}),
        Map.entry("Cairo", new double[]{30.0444, 31.2357}),
        Map.entry("Paris", new double[]{48.8566, 2.3522}),
        Map.entry("Berlin", new double[]{52.5200, 13.4050}),
        Map.entry("Moscow", new double[]{55.7558, 37.6173}),
        Map.entry("Beijing", new double[]{39.9042, 116.4074}),
        Map.entry("Mumbai", new double[]{19.0760, 72.8777}),
        Map.entry("Rio de Janeiro", new double[]{-22.9068, -43.1729}),
        Map.entry("Buenos Aires", new double[]{-34.6037, -58.3816}),
        Map.entry("Johannesburg", new double[]{-26.2041, 28.0473}),
        Map.entry("Dubai", new double[]{25.276987, 55.296249}),
        Map.entry("Singapore", new double[]{1.3521, 103.8198}),
        Map.entry("Los Angeles", new double[]{34.0522, -118.2437}),
        Map.entry("Chicago", new double[]{41.8781, -87.6298}),
        Map.entry("Toronto", new double[]{43.651070, -79.347015}),
        Map.entry("Mexico City", new double[]{19.4326, -99.1332}),
        Map.entry("Seoul", new double[]{37.5665, 126.9780}),
        Map.entry("Bangkok", new double[]{13.7563, 100.5018}),
        Map.entry("Istanbul", new double[]{41.0082, 28.9784}),
        Map.entry("Madrid", new double[]{40.4168, -3.7038}),
        Map.entry("Rome", new double[]{41.9028, 12.4964}),
        Map.entry("Kuala Lumpur", new double[]{3.1390, 101.6869}),
        Map.entry("Hong Kong", new double[]{22.3193, 114.1694}),
        Map.entry("Lagos", new double[]{6.5244, 3.3792}),
        Map.entry("Karachi", new double[]{24.8607, 67.0011}),
        Map.entry("Jakarta", new double[]{-6.2088, 106.8456})
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
            throw new RuntimeException("Failed to fetch weather data");
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
