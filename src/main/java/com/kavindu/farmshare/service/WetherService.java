package com.kavindu.farmshare.service;

import com.kavindu.farmshare.dto.WeatherDataDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WetherService {
    private static final String API_KEY = "####";
    private static final String BASE_URL = "http://api.weatherapi.com/v1/current.json";

    public WeatherDataDto getWeather(double lat, double lon) {
        String url = "http://api.weatherapi.com/v1/current.json?key="+API_KEY+"&q="+lat+","+lon;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, WeatherDataDto.class);
    }
}
