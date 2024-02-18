package blog.yrol.weather.service;


import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExternalServiceClient {

    /**
     * Using the map called "weather" and within that key is called "zip"
     * Returning a number between 60 - 100
     * **/
    @CachePut(value = "weather", key = "#zip")
    public int getWeatherInfo(int zip) {
        return ThreadLocalRandom.current().nextInt(60, 100);
    }
}
