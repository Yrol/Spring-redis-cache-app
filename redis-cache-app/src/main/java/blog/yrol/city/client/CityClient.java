package blog.yrol.city.client;

import blog.yrol.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CityClient
{

    private final WebClient webClient;

    /**
     * Building the webclient
     * **/
    public CityClient(@Value("${city.service.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    /** Getting a Mono of city via webclient **/
    public Mono<City> getCity(final String zipCode) {
        return this.webClient.get()
                .uri("{zipcode}", zipCode)
                .retrieve()
                .bodyToMono(City.class);
    }
}
