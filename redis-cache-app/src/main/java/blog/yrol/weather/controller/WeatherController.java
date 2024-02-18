package blog.yrol.weather.controller;


import blog.yrol.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * http://localhost:8080/weather/2
     * **/
    @GetMapping("{zip}")
    public Mono<Integer> getWeather(@PathVariable int zip) {
        return Mono.fromSupplier(()-> this.weatherService.getInfo(zip)).doOnNext(System.out::println);
    }


}
