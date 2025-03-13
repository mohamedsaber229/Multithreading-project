# 🌤 Weather App - Multi-threaded Weather Fetcher  

**Weather App** is a simple application that fetches weather data for specified cities using the [Open-Meteo API](https://open-meteo.com/). It leverages **multithreading** to enhance performance by retrieving weather data for multiple cities concurrently.  

## ✨ Key Components  

1. **`WeatherData.java`**  
   - Represents weather data for a specific city.  
   - Includes information such as:  
     - Location  
     - Temperature  
     - Wind speed and direction  

2. **`WeatherFetcher.java`**  
   - Responsible for fetching weather data using the city’s coordinates from the API.  
   - Implements the **`Callable<WeatherData>`** interface, enabling execution in **separate threads**, improving efficiency.  




