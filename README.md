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

# Flyway

### Using Maven

#### H2
 `mvn clean -Dflyway.configFiles=src/main/docker/flyway-h2.conf -Dflyway.locations=filesystem:src/main/resources/db/migration/ flyway:migrate`
#### Postgres
 `mvn clean -Dflyway.configFiles=src/main/docker/flyway-postgres.conf -Dflyway.url=jdbc:localhost://postgres:5432/postgres -Dflyway.locations=filesystem:src/main/resources/db/migration/ flyway:migrate`

### Other goals 
Official doc: https://flywaydb.org/documentation/usage/maven/

|   Name   | Description                                                                                  |
|:--------:|----------------------------------------------------------------------------------------------|
| migrate  | Migrates the database                                                                        |
|  clean   | Drops all objects in the configured schemas                                                  |
|   info   | Prints the details and status information about all the migrations                           |
| validate | Validates the applied migrations against the ones available on the classpath                 |
|   undo   | Flyway Teams 	Undoes the most recently applied versioned migration                           |
| baseline | Baselines an existing database, excluding all migrations up to and including baselineVersion |
|  repair  | Repairs the schema history table                                                             |

#### Connect to H2 Database from IntelliJ
    Database > Add New > H2
        URL: `jdbc:h2:file:./springboot-tutorial/target/foobar`<br>
        UserName: sa<br>
        Pwd: <Empty>

 ### Using Docker

Run `docker compose up -d` from `src/main/docker` folder.

Login to DBeaver