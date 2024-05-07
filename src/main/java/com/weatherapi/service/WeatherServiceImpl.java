package com.weatherapi.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.dao.CoordinatesData;
import com.weatherapi.dao.ForecastData;
import com.weatherapi.dao.WeatherData;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherServiceImpl implements WeatherService{

	private static final String API_KEY = "5a90dbea461838b9770353a1eaad376b";

	@Override
	public WeatherData getWeatherDataForCity(String city) {
        String forecastBaseUrl = "https://api.openweathermap.org/data/2.5/weather";
        URI uri = UriComponentsBuilder.fromHttpUrl(forecastBaseUrl)
                .queryParam("cityName", city)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Parse JSON response
                JsonNode root = mapper.readTree(responseEntity.getBody());

                // Define placeholder field names - modify these as necessary
                String tempField = "placeholderField";
                String feelsLikeField = "placeholderField";
                String rainField = "placeholderField";
                String cloudField = "placeholderField";
                String humidityField = "placeholderField";
                String pressureField = "placeholderField";
                String tempMinField = "placeholderField";
                String tempMaxField = "placeholderField";

                // Fetch data from JSON
                Double temp = root.path(tempField).asDouble();
                Double feelsLike = root.path(feelsLikeField).asDouble();
                Double rain = root.path(rainField).asDouble();
                String cloud = root.path(cloudField).asText();
                Double humidity = root.path(humidityField).asDouble();
                Double pressure = root.path(pressureField).asDouble();
                Double tempMin = root.path(tempMinField).asDouble();
                Double tempMax = root.path(tempMaxField).asDouble();

                // Create WeatherData object and set properties
                WeatherData weatherData = new WeatherData();
                weatherData.setTemp(temp);
                weatherData.setFeelsLike(feelsLike);
                weatherData.setRain(rain);
                weatherData.setCloud(cloud);
                weatherData.setHumidity(humidity);
                weatherData.setPressure(pressure);
                weatherData.setTempMin(tempMin);
                weatherData.setTempMax(tempMax);

                return weatherData;
            } catch (Exception e) {
                // Handle exception here
                e.printStackTrace();
            }
        } else {
            // handle error here
            System.out.println("There is an error in the data.");
        }
		return null;
	}

	@Override
	public List<ForecastData> getFourDayForecast(String city) {

        URI uri = UriComponentsBuilder.fromHttpUrl("https://pro.openweathermap.org/data/2.5/forecast/hourly")
                .queryParam("cityName", city)
                .queryParam("appid", API_KEY)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                // Parse JSON response
                JsonNode root = mapper.readTree(responseEntity.getBody());

                // Extract 'list' from JSON which contains forecast data
                JsonNode listNode = root.path("list");

                // Loop through each forecast element in 'list'
                Map<LocalDate, WeatherData> forecastMap = new HashMap<>();
                for (JsonNode forecastNode : listNode) {
                    // Fetch data from JSON
                    Double temp = forecastNode.path("main").path("temp").asDouble();
                    Double feelsLike = forecastNode.path("main").path("feels_like").asDouble();
                    Double pressure = forecastNode.path("main").path("pressure").asDouble();
                    Double humidity = forecastNode.path("main").path("humidity").asDouble();
                    Double tempMin = forecastNode.path("main").path("temp_min").asDouble();
                    Double tempMax = forecastNode.path("main").path("temp_max").asDouble();
                    String cloud = forecastNode.path("weather").get(0).path("description").asText();

                    // Create WeatherData object and set properties
                    WeatherData weatherData = new WeatherData();
                    weatherData.setTemp(temp);
                    weatherData.setFeelsLike(feelsLike);
                    weatherData.setPressure(pressure);
                    weatherData.setHumidity(humidity);
                    weatherData.setTempMin(tempMin);
                    weatherData.setTempMax(tempMax);
                    weatherData.setCloud(cloud);

                    // Get the date from 'dt_txt' field in 'list' array
                    String dateTime = forecastNode.path("dt_txt").asText();  // This is in format "2021-11-28 15:00:00"
                    LocalDate date = LocalDate.parse(dateTime.split(" ")[0]);  // Get only date part.

                    // Add to forecastMap
                    forecastMap.put(date, weatherData);
                }

                // Create ForecastData object and set properties
                ForecastData forecastData = new ForecastData();
                forecastData.setWeatherData(forecastMap);

                return List.of(forecastData);
            } catch (Exception e) {
                // Handle exception here
                e.printStackTrace();
            }
        }
        return List.of();
	}
}
