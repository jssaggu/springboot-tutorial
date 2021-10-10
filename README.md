# SpringBoot Tutorial

## Videos Available here

https://www.youtube.com/playlist?list=PLYwGWvgqiQCkgxraQU-fuOiYzNCcjGAlz

## Spring Profiles

#### How to run using a profile?

`mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles={Profile-Name}`

e.g. For Dev

```mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles={Profile-Name}```

e.g. Different Port

```mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080```
```mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8090```

## Spring Caching

### How to use Redis?

Update the following in application.yml
```yaml
spring:
  cache:
    redis:
      time-to-live: 100S
    type: redis
```

#### How to use install Redis using Docker?

* docker pull redis
* docker run --name saggu.uk -p 6379:6379 -d redis
