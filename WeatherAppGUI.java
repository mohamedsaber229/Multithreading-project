package com.mycompany.weatherproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WeatherAppGUI {

    private BuildThread buildThread;

    public WeatherAppGUI() {
        // ------------- Create JFrame -------------
        JFrame frame = new JFrame("Weather Data Aggregator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // ------------- UI Elements -------------
        JLabel threadLabel = new JLabel("Number of Threads:");
        JTextField threadInput = new JTextField();

        JLabel citiesLabel = new JLabel("Select City:");
        String[] cityOptions = {"New York", "London", "Tokyo", "Sydney", "Cairo", "Paris", "Berlin", "Moscow",
                "Beijing", "Mumbai", "Rio de Janeiro", "Buenos Aires", "Johannesburg", "Dubai",
                "Singapore", "Los Angeles", "Chicago", "Toronto", "Mexico City", "Seoul",
                "Bangkok", "Istanbul", "Madrid", "Rome", "Kuala Lumpur", "Hong Kong",
                "Lagos", "Karachi", "Jakarta", "Nairobi"};
        JComboBox<String> cityDropdown = new JComboBox<>(cityOptions);

        JButton addCityButton = new JButton("Add City");
        DefaultListModel<String> selectedCitiesModel = new DefaultListModel<>();
        JList<String> selectedCitiesList = new JList<>(selectedCitiesModel);
        JScrollPane cityScrollPane = new JScrollPane(selectedCitiesList);

        JButton startButton = new JButton("Start Fetching");
        JButton stopButton = new JButton("Stop");
        stopButton.setEnabled(false);

        JTable weatherTable = new JTable(new DefaultTableModel(
                new Object[]{"City", "Temperature (°C)", "Wind Speed (Km/h)", "Wind Direction (°)"}, 0
        ));
        JScrollPane tableScrollPane = new JScrollPane(weatherTable);

        // ------------- Layout -------------
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(threadLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(threadInput, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(citiesLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(cityDropdown, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(addCityButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(cityScrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(startButton, gbc);

        gbc.gridx = 2; gbc.gridy = 3;
        inputPanel.add(stopButton, gbc);

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // ** ---------- functions ----------

        // ------------- Add City Button -------------
        addCityButton.addActionListener(e -> {
            String selectedCity = (String) cityDropdown.getSelectedItem();
            if (!selectedCitiesModel.contains(selectedCity)) {
                selectedCitiesModel.addElement(selectedCity);
            }
        });

        // ------------- Start Button -------------
        startButton.addActionListener(e -> {
            try {
                int threadCount = Integer.parseInt(threadInput.getText());
                List<String> cities = new ArrayList<>();
                for (int i = 0; i < selectedCitiesModel.size(); i++) {
                    cities.add(selectedCitiesModel.getElementAt(i));
                }

                if (threadCount <= 0 || cities.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter valid thread count and select at least one city.");
                    return;
                }

                buildThread = new BuildThread(threadCount);
                buildThread.startWeatherThreads(cities, weatherTable);

                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number of threads.");
            }
        });


        // Stop Button
        stopButton.addActionListener(e -> {
            if (buildThread != null) {
                buildThread.shutdown();
            }
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        });

        frame.setVisible(true);
    }

//    private void fetchWeatherData_AddToTable(List<String> cities, JTable weatherTable) {
//
//        Runnable task = () -> {
//            SwingUtilities.invokeLater(() -> {
//                DefaultTableModel model = (DefaultTableModel) weatherTable.getModel();
//                model.setRowCount(0); // Clear old data
//            });
//
//            List<Future<WeatherData>> futures = buildThread.fetchWeatherData(cities);
//
//
//            for (Future<WeatherData> future : futures) {
//                try {
//                    WeatherData data = future.get();
//                    SwingUtilities.invokeLater(() -> {
//                        DefaultTableModel model = (DefaultTableModel) weatherTable.getModel();
//                        model.addRow(new Object[]{
//                                data.getLocation(),
//                                data.getTemperature(),
//                                data.getWindSpeed(),
//                                data.getWindDirection()
//                        });
//                    });
//                } catch (Exception e) {
//                    System.err.println("Error fetching data: " + e.getMessage());
//                }
//            }
//        };
//
//        buildThread.scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
//
//    }
}
