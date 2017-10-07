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

## Security
Api authentication is done using spring security and JWT.
To authenticate, you need to do a *POST* request on the **/api/login** endpoint and give it the following a request body :

```
{"username":"admin","password":"password"}
```

Example curl request :
```
curl -i -X POST \
  http://localhost:8080/api/login \
  -H 'cache-control: no-cache' \
  -H "Accept: application/json" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

Then you get an authorization bearer in headers, you can copy it and do another request with its value as a header :

```
curl -i -X GET \
  http://localhost:8080/api/supervisors \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTQ5NjE0Nzk5NX0.U0bW5-_QmwBpVnf0bEK1Tg6LwF5QgSnb8ekYXygh-Zxor-ujm_btAMPz5Exw4FVGg2-oQNJAQnl-B2peuG8nlA' \
  -H 'cache-control: no-cache' \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

All */api/supervisors* require authentication as an example.

## Architecture

There is just a mongodb docker container and a springboot app to run.
You can define a mongodb database manually or execute *scripts/CreateDockerDatabases_x64*. Then you can start or stop it using docker cli.


## Technologies used

  - API : Spring MVC app (Java) with springboot
  - Persistence : Mongodb NoSQL Database (Document oriented)


## Documentation

When you run the api, you can access the address *localhost:8080/*, it will redirect you to the swagger-ui.
From there you'll have access to all routes and be able to test them.
