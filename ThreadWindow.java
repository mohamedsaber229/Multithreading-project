package com.mycompany.weatherproject;

import javax.swing.*;
import java.awt.*;

public class ThreadWindow {
    private final JFrame frame;
    private final JLabel temperatureLabel;
    private final JLabel windSpeedLabel;
    private final JLabel windDirectionLabel;

    public ThreadWindow(String city) {
        // Create frame
        frame = new JFrame("Weather Data - " + city);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create UI elements
        JLabel cityLabel = new JLabel("City: " + city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 16));

        temperatureLabel = new JLabel("Temperature: N/A");
        windSpeedLabel = new JLabel("Wind Speed: N/A");
        windDirectionLabel = new JLabel("Wind Direction: N/A");

        // Layout
        frame.setLayout(new GridLayout(4, 1));
        frame.add(cityLabel);
        frame.add(temperatureLabel);
        frame.add(windSpeedLabel);
        frame.add(windDirectionLabel);

        frame.setVisible(true);
    }

    public void updateWeatherData(double temperature, double windSpeed, double windDirection) {
        SwingUtilities.invokeLater(() -> {
            temperatureLabel.setText(String.format("Temperature: %.2f°C", temperature));
            windSpeedLabel.setText(String.format("Wind Speed: %.2f km/h", windSpeed));
            windDirectionLabel.setText(String.format("Wind Direction: %.2f°", windDirection));
        });
    }
}
