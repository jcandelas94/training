# Spring Boot Server Application Archetype

## Requirements

* JDK 19+
* Docker

## Building

```
./mvn install
```

## Running

```
./mvnw spring-boot:run
```

## Docker build and running

* In this project's path, inside the folder "/wiremock-main", run:
```
docker run -it --rm -p 8081:8080 --name wiremock -v $PWD:/home/wiremock wiremock/wiremock:3.9.1
```

## USER AUTHORIZATION

* Authenticated users have access to the whole application, via basic auth.
* There are two users in-memory:
* user1: 00000000A, pass: 666
* user1: 88888888B, pass: 666