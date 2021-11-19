package com.example.demo.controller;

import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class WeatherClassController {

    private final String apiKey = "2YQpMaGUI740JVGEGRVbVUdFsHmjjfL8";

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "Get details for weather", response = WeatherClassController.class, tags = "getWeather")
    @RequestMapping(value = "/city/{key}", method = RequestMethod.GET)
    public String getWeather(@PathVariable String key)
    {
        return restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=" + apiKey + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, key).getBody();
    }

    @ApiOperation(value = "Get weather by city", response = WeatherClassController.class, tags = "getCityWeather")
    @RequestMapping(value = "/getCityWeather/{city}", method = RequestMethod.GET)
    public String getCityWeather(@PathVariable String city) {
       return jsonParser(restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apiKey + "&q=" + city + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, city).getBody());
    }

    public String jsonParser(String json) {
        json = json.substring(1, json.length() - 1);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);
        String key = (String) jsonJavaRootObject.get("Key");
        return getWeather(key);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
