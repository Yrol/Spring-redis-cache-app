package blog.yrol.city.service;

import blog.yrol.city.client.CityClient;
import blog.yrol.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

//    private RMapReactive<String, City> cityMap; // will not expose field expiration

    // using RMapCacheReactive to enable field expiration
    private RMapCacheReactive<String, City> cityMap;

    public CityService(RedissonReactiveClient client) {
        this.cityMap = client.getMapCache("city", new TypedJsonJacksonCodec(String.class, City.class));
    }

    /**
     * Building the pipeline for the following
     * 1. Attempting to get values from cache
     * 2. If cache is empty get from DB / source, then save that in cache and return
     * 3. Expiring each key in 20 seconds
     * **/
    public Mono<City> getCity(final String zipCode) {
        return this.cityMap.get(zipCode) // attempting to get value from cache
                .switchIfEmpty(this.cityClient.getCity(zipCode)  // if empty get city from external service
//                        .flatMap(c -> this.cityMap.fastPut(zipCode, c // save city info to cache without expiration
                        .flatMap(c -> this.cityMap.fastPut(zipCode, c,20, TimeUnit.SECONDS // save city info to cache with expiration (in 20 seconds)
                        ).thenReturn(c))
                );
    }
}
