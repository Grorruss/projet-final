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

    private final String apiKey = "jsNPTmcWGr6g4Oov6lpcz0iTbTE9QxUb";

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "Get details for weather", response = WeatherClassController.class, tags = "getWeather")
    @RequestMapping(value = "/city/{key}", method = RequestMethod.GET)
    public String getWeather(@PathVariable String key)
    {
        System.out.println("Getting Weather details for " + key);
        //System.out.println("Response Body " + response);
        return restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=" + apiKey + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, key).getBody();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
