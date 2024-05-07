package com.weatherapi.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    private Double temp;
    private Double feelsLike;
    private Double rain;
    private String cloud;
    private Double humidity;
    private Double pressure;
    private Double tempMin;
    private Double tempMax;
}
