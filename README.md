# SpringBoot Tutorial
![Java Maven CI](https://github.com/jssaggu/springboot-tutorial/actions/workflows/maven.yml/badge.svg)

## Videos Available here
All the tutorial videos are available on Saggu.uk YouTube channel.

[![Watch the video](docs/Spring-Tutorial-Playlist.png)](https://www.youtube.com/playlist?list=PLYwGWvgqiQCkgxraQU-fuOiYzNCcjGAlz)

## Spring Profiles

#### How to run using a profile?

```
mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles={Profile-Name}
```

e.g. For Dev

```
mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles={Profile-Name}
```

e.g. Different Port

```
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8090
```

or

```
mvn clean install -DskipTests
java -jar target/springboot-tutorial-0.0.1-SNAPSHOT.jar --server.port=8080 --management.server.port=9090
```

## Spring Caching

*Note:* Just enable one cache provider

### Redis

###### Dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>${cache.version}</version>
</dependency>
```

###### application.yml
Add the following: 
```yaml
spring:
  cache:
    redis:
      time-to-live: 100S
    type: redis
```

###### How to install Redis using Docker?

* docker pull redis
* docker run --name saggu.uk -p 6379:6379 -d redis

### Hazelcast

###### Dependency
```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-all</artifactId>
    <version>4.2.4</version>
</dependency>
```

###### Add hazelcast.yaml
```yaml
hazelcast:
  network:
    join:
      multicast:
        enabled: true
```