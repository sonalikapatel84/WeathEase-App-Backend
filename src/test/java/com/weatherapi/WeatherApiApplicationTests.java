package com.weatherapi;

import com.weatherapi.dao.ForecastData;
import com.weatherapi.service.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class WeatherApiApplicationTests {

	@Autowired
	private WeatherServiceImpl weatherService;

	private MockRestServiceServer mockServer;
	private RestTemplate restTemplate;

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
		weatherService = new WeatherServiceImpl(restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void testGetFourDayForecast() {
		String responseBody = "{\"placeholderField\":\"Test data\"}";  //Replace with representative API response

        double lat = 12.9715987; // replace with actual latitude value
        double lon = 77.5945627; // replace with actual longitude value
        final String API_KEY = "your_api_key"; // replace with actual API key
		mockServer.expect(ExpectedCount.once(),
						requestTo(
								String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s",
										lat, lon, API_KEY)))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

		List<ForecastData> forecastData = weatherService.getFourDayForecast(
				12.9715987,77.5945627); // Given latitude and longitude

		mockServer.verify();

		assertEquals(1, forecastData.size());
//		assertEquals("Test data", forecastData.get(0).getPlaceholderField()); //TODO
	}

}
