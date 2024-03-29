package blog.yrol.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class WeatherService {

    @Autowired
    private ExternalServiceClient client;

    /**
     * By default, return 0 if the scheduler has not assign values to the integer from 0 to 5
     * **/
    @Cacheable("weather")
    public int getInfo(int zip) {
        return 0;
    }

    /**
     * Assigning a random values (60 - 100) to the integers 0 - 5
     * **/
    @Scheduled(fixedRate = 10000)
    public void update() {
        System.out.println("updating weather");
        IntStream.rangeClosed(1, 5)
                .forEach(this.client::getWeatherInfo);
    }
}
