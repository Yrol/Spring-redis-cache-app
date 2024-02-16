package blog.yrol.fib.controller;

import blog.yrol.fib.service.FibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fib")
public class FibController {

    @Autowired
    private FibService service;

    @GetMapping("{index}/{name}")
    public Mono<Integer> getFib(@PathVariable int index, @PathVariable String name) {
        return Mono.fromSupplier(() -> this.service.getFib(index, name));
    }

    /**
     * Api call for clearing cache.
     * GET call, but ideally this should be a PATCH / PUT / POST / DELETE when the data is impacted
     * **/
    @GetMapping("{index}/clear")
    public Mono<Void> clearCache(@PathVariable int index) {
        return Mono.fromRunnable(() -> this.service.clearCache(index));
    }
}
