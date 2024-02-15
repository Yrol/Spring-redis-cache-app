package blog.yrol.test;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class RedisSpringApplicationTests {

    @Autowired
    private ReactiveStringRedisTemplate template;

    @Autowired
    private RedissonReactiveClient reactiveClient;

    /**
     * Using Spring Data Reactive Redis lib to insert 10000 records (one by one)
     * Repeating test for 3 times and Ignore the first test run and check the time consumed my test run 2 & 3.
     * */
    @RepeatedTest(3)
    void springDataRedisTest() {
        ReactiveValueOperations<String, String> valueOperations = this.template.opsForValue();

        long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 10000 )
                .flatMap(i -> valueOperations.increment("user:1:visit"))
                .then();

        StepVerifier.create(mono)
                .verifyComplete();

        long after = System.currentTimeMillis();
        System.out.println((after - before) + " ms");
    }

    /**
     * Using Reddison lib to insert 10000 records (one by one)
     * Repeating test for 3 times and Ignore the first test run and check the time consumed my test run 2 & 3.
     * Compared to Spring Data Reactive Redis lib above, this improves performance significantly.
     * */
    @RepeatedTest(3)
    void RedissonTest() {
        RAtomicLongReactive atomicLongReactive = this.reactiveClient.getAtomicLong("user:2:visit");

        long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 10000 )
                .flatMap(i -> atomicLongReactive.incrementAndGet())
                .then();

        StepVerifier.create(mono)
                .verifyComplete();

        long after = System.currentTimeMillis();
        System.out.println((after - before) + " ms");
    }

}
