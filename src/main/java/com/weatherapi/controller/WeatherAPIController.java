package com.weatherapi.controller;

import java.util.List;

import com.weatherapi.dao.ForecastData;
import com.weatherapi.dao.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WeatherAPIController{

	@Autowired
	WeatherService wService;


	// An endpoint that provides the current weather based on city
	@GetMapping("/current/weather")
	public ResponseEntity<WeatherData> getCurrentWeatherDataForCity(
			@RequestParam("city") String city) {

		// Your implementation which fetches the weather data for given city
		// and put that data into a WeatherData object
		WeatherData weatherData = wService.getWeatherDataForCity(city);

		// return the weatherData object as a response entity with status OK
		return ResponseEntity.ok(weatherData);
		
	}

	@GetMapping("/fourDayForecast")
	public ResponseEntity<List<ForecastData>> getFourDayForecast(
			@RequestParam("city") String city) {

		// Your implementation which fetches the forecast data for given city
		// and put that data into a List of ForecastData objects
		List<ForecastData> forecastDataList = wService.getFourDayForecast(city);

		// return the forecastDataList object as a response entity with status OK
		return ResponseEntity.ok(forecastDataList);
	}
}
