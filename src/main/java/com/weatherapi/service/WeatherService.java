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
	WeatherData getWeatherDataForCity(String city);

	/**
	 * @param city
	 * @return
	 */
	List<ForecastData> getFourDayForecast(String city);
}
