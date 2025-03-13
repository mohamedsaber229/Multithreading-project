package com.weatherapp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("New York", "London", "Tokyo", "Cairo", "Paris");

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<WeatherData>> futures = cities.stream()
                .map(city -> executorService.submit(new WeatherFetcher(city)))
                .toList();

        futures.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error fetching weather data: " + e.getMessage());
            }
        });

        executorService.shutdown();
    }
}
