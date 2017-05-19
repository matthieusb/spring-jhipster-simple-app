# spring-simple-api
The point of this project is to design a small spring rest api that communicates with a mongodb app, using json to do classic CRUD operations.

[![Build Status](https://travis-ci.org/matthieusb/spring-simple-api.svg?branch=master)](https://travis-ci.org/matthieusb/spring-simple-api)
[![GetBadges Game](https://matthieusb-spring-simple-app.getbadges.io/shield/company/matthieusb-spring-simple-app/user/15203)](https://matthieusb-spring-simple-app.getbadges.io/?ref=shield-player)

## Building/Testing/Compiling

To simply run the application :

```
mvn spring-boot:run
```

If you want to run the tests :

```
mvn test
```

For load tests with *gatling*, run :

```
./gatling_tests.sh
```
This will start the app and then launch the load tests on it.
You'll see some result in your console, but you can go check out the graphs in *target/gatling* afterwards and open the index.html for nice looking graphs (Can also be done using `mvn clean install`)

To launch all tests and loading tests as well :

```
mvn verify
```


To generate the checkstyle report (Also see the intellij integration plugin) :


```
mvn checkstyle:check
```

To generate all jars and launch all tests :
 
```
mvn clean install
```


## Architecture

There is just a mongodb docker container and a springboot app to run. More will likely come. 

## Technologies used

  - API : Spring MVC app (Java) with springboot
  - Persistence : Mongodb NoSQL Database (Document oriented)

## Documentation

When you run the api, you can access the address *localhost:8080/*, it will redirect you to the swagger-ui.
From there you'll have access to all routes and be able to test them.

## Evolutions

Another version of this simple api should come with Spring reactive and maybe Kotlin on my github repository.

