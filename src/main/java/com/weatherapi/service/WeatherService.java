package com.weatherapi.service;

import com.weatherapi.dao.CoordinatesData;
import com.weatherapi.dao.WeatherData;
import com.weatherapi.dao.ForecastData;

import java.util.List;

public interface WeatherService {

	/**
	 * @param city
	 * @return
	 */
	CoordinatesData getCoordinatesForCity(String city);

	/**
	 * @param lat
	 * @param lon
	 * @return
	 */
	WeatherData getWeatherDataForCoordinates(double lat, double lon);

	/**
	 * @param lat
	 * @param lon
	 * @return
	 */
	List<ForecastData> getFourDayForecast(double lat, double lon);
}
