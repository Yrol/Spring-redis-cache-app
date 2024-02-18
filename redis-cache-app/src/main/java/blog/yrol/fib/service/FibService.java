package blog.yrol.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    /**
     * @Cacheable - Caching data using the index / hash key "math:fib"
     * If the value don't exist, execute the method and store it in Redis when returning the response, otherwise fetch it from Redis
     * Making sure the cacheable key is the index (not the name). Hence, a different name with same value provided will be calculated from the cache if available
     * **/
    @Cacheable(value = "math:fib", key = "#index")
    public int getFib(int index, String name) {
        System.out.println("Calculating fib for +" + index + " name:" + name);
        return this.fib(index);
    }

    /**
     * Cache evict - clearing the cache
     * Cache evit should ideally be performed when the data is impacted. Ex: PATCH / PUT / POST / DELETE
     * @CacheEvict annotation can be bound with service method which perform the PATCH / PUT / POST / DELETE
     * Clearing the cache for the index provided which may be available inside math:fib
     * **/
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("clearing the cache for:" + index);
    }


    /**
     * Scheduled cache evict (in every 10 seconds)
     * **/
    @Scheduled(fixedRate = 10000)
    @CacheEvict(value = "math:fib", allEntries = true)
    public void clearCacheScheduled() {
        System.out.println("clearing all fib keys after every 10 seconds");
    }

    private int fib(int index) {
        if(index < 2){
            return index;
        }
        return fib(index - 1) + fib(index - 2);
    }
}
