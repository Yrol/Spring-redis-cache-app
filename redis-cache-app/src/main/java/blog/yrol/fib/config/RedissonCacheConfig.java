package blog.yrol.fib.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonCacheConfig {

    /**
     * Exposing a cache manager
     * If this bean is not exposed, SpringBoot will use a concurrent hashmap by default to store cache.
     * This will make a centralised location for storing the cached data
     * **/
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient);
    }


}
