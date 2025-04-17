package com.mycompany.weatherproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.*;

public class BuildThread {
    // ExecutorService => interface in Java that provides methods to manage and control a pool of worker threads.
    private final ExecutorService threadPool;
    private final Map<String, ThreadWindow> cityWindows;

    public BuildThread(int threadCount) {
        threadPool = Executors.newFixedThreadPool(threadCount);
        cityWindows = new ConcurrentHashMap<>();
    }

    public void startWeatherThreads(List<String> cities, JTable weatherTable) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable fetchTask = () -> {
            DefaultTableModel model = (DefaultTableModel) weatherTable.getModel();
            // submit a task (as Callable) to an ExecutorService, it returns a Future object:
            List<Future<WeatherData>> futures = cities.stream()
                    .map(city -> threadPool.submit(new WeatherFetcher(city)))
                    .toList();

            for (Future<WeatherData> future : futures) {
                try {
                    WeatherData data = future.get();  // Block until result is ready

                    // Update JTable
                    SwingUtilities.invokeLater(() -> {
                        for (int i = model.getRowCount() - 1; i >= 0; i--) {
                            if (model.getValueAt(i, 0).equals(data.getLocation())) {
                                model.removeRow(i);
                            }
                        }
                        model.addRow(new Object[]{
                                data.getLocation(),
                                data.getTemperature(),
                                data.getWindSpeed(),
                                data.getWindDirection()
                        });
                    });

                    // Create or update ThreadWindow
                    cityWindows.computeIfAbsent(data.getLocation(), city -> new ThreadWindow(city))
                            .updateWeatherData(data.getTemperature(), data.getWindSpeed(), data.getWindDirection());

                } catch (Exception e) {
                    System.err.println("Error fetching data: " + e.getMessage());
                }
            }
        };

        scheduler.scheduleAtFixedRate(fetchTask, 0, 5, TimeUnit.SECONDS);
    }

    public void shutdown() {
        threadPool.shutdownNow();
//        cityWindows.values().forEach(window -> SwingUtilities.invokeLater(window::dispose));
    }
}
