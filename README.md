# 🌤 # Weather App

This project is a simple Java application that fetches weather data for multiple cities using the [Open-Meteo](https://open-meteo.com/) API. It leverages **multithreading** to enhance performance by retrieving weather data concurrently.

## Features
Multi-threaded Weather Fetching
Utilizes a configurable thread pool to fetch weather data concurrently.

Real-Time Updates
Automatically refreshes weather data every 5 seconds.

Interactive GUI

Table view showing weather information for all selected cities.

Individual pop-up windows for each city with detailed weather data.

City selection interface with dropdown menu and management list.

Robust Error Handling
Graceful handling of errors during API requests and data parsing.

🧩 Components
WeatherAppGUI – Main application window with controls and weather table.

BuildThread – Manages the thread pool and coordinates weather fetching tasks.

WeatherFetcher – A Callable task responsible for fetching weather data from the Open-Meteo API.

ThreadWindow – Separate window displaying detailed weather info for a specific city.

WeatherData – Data structure to store and organize weather details.

🌍 Supported Cities
The application supports weather data for 29 major cities worldwide:

New York, London, Tokyo, Sydney, Cairo, Paris, Berlin, Moscow, Beijing, Mumbai,
Rio de Janeiro, Buenos Aires, Johannesburg, Dubai, Singapore, Los Angeles, Chicago, Toronto,
Mexico City, Seoul, Bangkok, Istanbul, Madrid, Rome, Kuala Lumpur, Hong Kong, Lagos, Karachi, Jakarta

🔧 Technical Details
API: Open-Meteo API for real-time weather data.

Threading:

Fixed Thread Pool for concurrent API calls.

Scheduled Executor for periodic updates.

GUI: Built with Swing for desktop interface.

JSON Parsing: Handled via the Gson library.

Concurrency: Uses ConcurrentHashMap for thread-safe data operations.


