# 🌤 # Weather App

This project is a simple Java application that fetches weather data for multiple cities using the [Open-Meteo](https://open-meteo.com/) API. It leverages **multithreading** to enhance performance by retrieving weather data concurrently.

## Key Features
- **Multithreading with ExecutorService** to fetch data for multiple cities in parallel.
- **Uses `Callable<WeatherData>`** to allow asynchronous execution and result retrieval.
- **Thread Pool Management** to optimize resource usage.
- **JSON Parsing with Gson** to process API responses.

## Project Structure

1. **`WeatherData.java`**
   - Represents weather data for a city, including temperature, wind speed, and wind direction.

2. **`WeatherFetcher.java`**
   - Implements `Callable<WeatherData>`, making it executable in a separate thread.
   - Fetches weather data for a given city from the API.

3. **`Main.java`**
   - Creates a thread pool using `ExecutorService`.
   - Submits multiple `WeatherFetcher` tasks for concurrent execution.
   - Retrieves and prints weather data from `Future<WeatherData>`.


## Multithreading Explanation
- **ExecutorService** is used to manage a thread pool efficiently.
- **Each city request is executed in a separate thread** using `executor.submit(fetcher)`.
- **`Future<WeatherData>` stores the result asynchronously**, allowing us to retrieve data when ready.
- **Parallel execution improves performance** by reducing waiting time for API responses.

### Sample Output
```
Weather in New York: 15.0°C, Wind Speed: 10.5 km/h, Wind Direction: 45.0°
Weather in London: 12.0°C, Wind Speed: 8.0 km/h, Wind Direction: 90.0°
Weather in Tokyo: 20.0°C, Wind Speed: 5.0 km/h, Wind Direction: 180.0°
Weather in Cairo: 30.0°C, Wind Speed: 12.0 km/h, Wind Direction: 270.0°
Weather in Paris: 18.0°C, Wind Speed: 7.5 km/h, Wind Direction: 135.0°
```



