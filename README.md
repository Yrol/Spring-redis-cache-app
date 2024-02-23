## Redis sample projects
This consist of sample projects that demonstrates various applications of Redis.

### Prerequisites
- Docker
- Java 11 or higher

### Running the application
**Step 1:** Go to `docker` folder and execute command below to spin up the Redis server.
```
docker-compose up
```

**Step 2:** Build and run the Spring Application via intelliJ

**Step 3:** Go to `open-city-api` folder and run the `city-api` supportive app.
```
java -jar city-api.jar
```

### Application 1: fibonacci generation
Simple application that stores the fibonacci value in Redis upon retrieval. 
The entire cache will be cleared after 10 seconds.

API call examples:
- [http://localhost:8080/fib/45/jake](http://localhost:8080/fib/45/jake)

The caching in the above request is determined by the number in the URL. Therefore, modifying the name while keeping the same number will not trigger a cache refresh if the content is already cached.



### Application 2: Temperature display app
A temperature display application that simulates fetching data from an external source automatically and store them in Redis for later retrieval. 
In this example it'll assign a random value to the integers range from 1 - 5 treated as states or cities.
Similar to a weather app, these values will be updated every 10 seconds. The application architecture is as below.
![](https://i.imgur.com/JCLRzEL.png)

As shown above when user makes an API call, the service will fetch the frequently updated vales from the Redis 
server instead of pulling data from an external service.

API call examples (only 0 - 5 is allowed):
- [http://localhost:8080/weather/2](http://localhost:8080/weather/2)


### Application 3: City info by zipcode
A simple city info app which will try to fetch city information for a given zipcode 
from cache first and if empty, pull information from the web service and save it in cache and 
also set the expiration for each value as 20 seconds while saving. This demonstrates that each key can 
be set to expire instead of expiring the whole hash.

API call examples:
- http://localhost:8080/city/10001

More zipcodes are available in `open-city-api/us.json`