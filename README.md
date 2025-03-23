# SpringBoot Tutorial
![Java Maven CI](https://github.com/jssaggu/springboot-tutorial/actions/workflows/maven.yml/badge.svg)

A comprehensive Spring Boot tutorial project demonstrating various Spring Boot features and best practices.

## Video Tutorials
All tutorial videos are available on the Saggu.uk YouTube channel.

[![Watch the video](docs/Spring-Tutorial-Playlist.png)](https://www.youtube.com/playlist?list=PLYwGWvgqiQCkgxraQU-fuOiYzNCcjGAlz)

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Features](#features)
  - [Spring Profiles](#spring-profiles)
  - [Spring Caching](#spring-caching)
  - [Database Migration](#flyway)
- [Development](#development)
- [Contributing](#contributing)

## Prerequisites
- Java 17 or later
- Maven 3.6+
- Docker (optional, for running Redis and databases)
- IDE (IntelliJ IDEA recommended)

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/jssaggu/springboot-tutorial.git
cd springboot-tutorial
```

2. Build the project:
```bash
mvn clean install -DskipTests
```

3. Run the application:
```bash
mvn spring-boot:run
```

## Project Structure
```
src/
├── main/
│   ├── java/          # Java source code
│   ├── resources/     # Configuration files
│   └── docker/        # Docker configuration
└── test/             # Test files
```

## Features

### Spring Profiles

Spring profiles allow you to configure different environments (dev, prod, test) for your application.

#### How to run using a profile?

```bash
mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles={Profile-Name}
```

Examples:
- For Dev profile:
```bash
mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles=dev
```

- For different ports:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

Or using the JAR:
```bash
java -jar target/springboot-tutorial-0.0.1-SNAPSHOT.jar --server.port=8080 --management.server.port=9090
```

### Spring Caching

The project supports multiple caching providers. **Note:** Enable only one cache provider at a time.

#### Redis Cache

1. Add the dependency:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>${cache.version}</version>
</dependency>
```

2. Configure in `application.yml`:
```yaml
spring:
  cache:
    redis:
      time-to-live: 100S
    type: redis
```

3. Run Redis using Docker:
```bash
docker pull redis
docker run --name saggu.uk -p 6379:6379 -d redis
```

#### Hazelcast Cache

1. Add the dependency:
```xml
<dependency>
    <groupId>com.hazelcast</groupId>
    <artifactId>hazelcast-all</artifactId>
    <version>4.2.4</version>
</dependency>
```

2. Add `hazelcast.yaml`:
```yaml
hazelcast:
  network:
    join:
      multicast:
        enabled: true
```

### Database Migration (Flyway)

#### Using Maven

For H2:
```bash
mvn clean -Dflyway.configFiles=src/main/docker/flyway-h2.conf -Dflyway.locations=filesystem:src/main/resources/db/migration/ flyway:migrate
```

For PostgreSQL:
```bash
mvn clean -Dflyway.configFiles=src/main/docker/flyway-postgres.conf -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.locations=filesystem:src/main/resources/db/migration/ flyway:migrate
```

#### Available Flyway Commands

| Command   | Description                                                                                  |
|-----------|----------------------------------------------------------------------------------------------|
| migrate   | Migrates the database                                                                        |
| clean     | Drops all objects in the configured schemas                                                  |
| info      | Prints the details and status information about all the migrations                           |
| validate  | Validates the applied migrations against the ones available on the classpath                 |
| undo      | Undoes the most recently applied versioned migration (Flyway Teams)                         |
| baseline  | Baselines an existing database, excluding all migrations up to and including baselineVersion |
| repair    | Repairs the schema history table                                                             |

#### Database Connection

##### H2 Database
- URL: `jdbc:h2:file:./springboot-tutorial/target/foobar`
- Username: `sa`
- Password: (empty)

##### Using Docker
Run the following command from the `src/main/docker` directory:
```bash
docker compose up -d
```

## Development

### IDE Setup
For IntelliJ IDEA:
1. Open the project
2. Import as Maven project
3. Enable annotation processing
4. Configure H2 database connection as described above

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
