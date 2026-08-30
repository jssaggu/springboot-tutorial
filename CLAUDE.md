# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A single-module Spring Boot 4.1.x / Java 21 tutorial app (`com.saggu:springboot-tutorial`). It is a teaching project accompanying the Saggu.uk YouTube series — each package demonstrates one Spring feature (config properties, profiles, caching, Actuator health, Flyway, logging groups), so code favours illustrative clarity over production hardening. Base package is `com.saggu.eshop`.

Spring Boot 4 notes that matter here (see git history for the 3.2 → 4.1 upgrade):
- `spring-boot-starter-web` is now `spring-boot-starter-webmvc`; Flyway comes via `spring-boot-starter-flyway`.
- Actuator was split into fine-grained modules: `HealthIndicator`/`Health` moved to `org.springframework.boot.health.contributor`.
- `RestClient` autoconfig is its own module (`spring-boot-starter-restclient`) — the Spring Boot Admin client needs it at runtime.
- Test HTTP helpers moved: `TestRestTemplate` is now `org.springframework.boot.resttestclient.TestRestTemplate` (artifact `spring-boot-resttestclient`, test scope) and requires `@AutoConfigureTestRestTemplate` on the test class.
- Per-endpoint `management.endpoint.<id>.enabled` is replaced by `management.endpoint.<id>.access`.
- OkHttp 5's `okhttp` artifact is an empty KMP aggregator; depend on `okhttp-jvm` for Maven.

## Commands

Use the Maven wrapper (`./mvnw`); plain `mvn` also works if installed.

- Build / full verify: `./mvnw -B package` (this is what CI runs)
- Build skipping tests: `./mvnw clean install -DskipTests`
- Run all tests: `./mvnw test`
- Run one test class: `./mvnw test -Dtest=ProductControllerTest`
- Run one test method: `./mvnw test -Dtest=ProductDaoTest#givenPrePopulatedData_getProducts_ShouldReturnAProductList`
- Run the app: `./mvnw spring-boot:run`
- Run with a profile: `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev` (also `prod`, `redis`)
- Apply code formatting: `./mvnw spotless:apply`

### Spotless / formatting gotcha

The `spotless-maven-plugin` binds `spotless:check` to the **compile** phase, so `package`, `test`, and `spring-boot:run` will **fail the build on any formatting violation**. Java is formatted with google-java-format **AOSP style** (4-space indent); that formatter version needs JDK 21+ to run (which the project already targets). Markdown/`.gitignore` are also checked (tabs, trailing whitespace, trailing newline). `ratchetFrom` is `origin/main`, so only files changed relative to `origin/main` are enforced — run `./mvnw spotless:apply` before committing.

## Runtime / ports

- App (Tomcat) listens on **8080**; Actuator management endpoints are on a **separate port, default 8081** (`management.server.port`), all endpoints exposed.
- Tomcat is deliberately throttled (`threads.max: 5`, `max-connections: 10`) to demonstrate connection-pool behaviour — see `OkHttpTest` which hammers the endpoint from 10 threads.
- Override ports: `java -jar target/springboot-tutorial-0.0.1-SNAPSHOT.jar --server.port=8080 --management.server.port=9090`
- OpenAPI UI (springdoc) at `/swagger-ui.html`; disabled in the `prod` profile.
- Registers with Spring Boot Admin at `http://localhost:9090` on startup (`spring.boot.admin.client`) — harmless if no Admin server is running.

## Architecture

Layering is `controller -> dao -> dto`, all under `com.saggu.eshop`:

- **DAOs are in-memory, not JPA.** `ProductDao` / `OrderDao` hold `HashMap`s and use `static` counters for IDs. `ProductDao` methods call `Thread.sleep` (2–5s) on purpose to make Spring Cache observable.
- **Caching**: `@EnableCaching` on the main class; `ProductDao` uses `@Cacheable` / `@CachePut` on cache `"products"`. Default provider is the no-op `simple` (in-memory `ConcurrentHashMap`) cache from `spring-boot-starter-cache`. Redis and Hazelcast deps are commented out in `pom.xml`; the `redis` profile (`application-redis.yml`) and `hazelcast.yaml` show how to switch — enable **only one** provider. Cache/Flyway/H2/Postgres versions are managed by the Spring Boot BOM (no explicit `<version>`).
- **Config properties**: `EshopProperties` binds prefix `jss`; `ProductDao` reads `products.prefix` directly via `@Value` (set to `"[Sample] "` in the `dev` profile).
- **Profiles**: `dev` enables `SystemInfoPrinter` (`GET /v1/system-info`) and the product prefix; `prod` turns off Swagger/api-docs. Profile config is split across `application-prod.yml` (which also contains a `---` `on-profile: dev` document) and `application-redis.yml`.
- **Flyway**: migrations in `src/main/resources/db/migration` (`V*` versioned, `U*` undo). Two ways to trigger — the Maven `flyway` plugin (see README for the `-Dflyway.configFiles=...` invocations) or `FlywayController` (`GET /flyway/v1/migrate`), which builds its own `Flyway` instance hard-coded to `jdbc:postgresql://localhost:5432/postgres`. Spring's auto-Flyway is commented out in `application.yml`.
- **Actuator**: `JSSHealthIndicator` is a custom `HealthIndicator` (from `org.springframework.boot.health.contributor`) contributing to `/actuator/health`. Boot 4 also adds `livenessState`/`readinessState`/`ssl` health components by default.
- **Logging**: `logback-spring.xml` (console + rolling file under `./logs`, `com.saggu` at TRACE). `application.yml` defines logging *groups* (`eshop`, `eshop-dao`, `eshop-controller`) mapping to packages.
- **Tests**: `src/test/resources/application.yml` disables the Spring Boot Admin client so the full-context tests don't spam registration failures. `ProductControllerTest` is a `@SpringBootTest(RANDOM_PORT)` + `@AutoConfigureTestRestTemplate`; `ProductDaoTest` news up the DAO directly (no Spring).
- `hashing/ConsistentHash` and `test/.../OkHttpTest` are standalone `main` demos, unrelated to the web app.

## Infrastructure

- `src/main/docker/docker-compose.yml` — Postgres (`admin`/`admin`) + a one-shot Flyway container. Run from `src/main/docker/`.
- H2 file DB used by tests/local: `jdbc:h2:file:./springboot-tutorial/target/foobar`, user `sa`, no password.

## CI

`.github/workflows/maven.yml` — on push/PR to `main`: JDK 21 (Temurin) + `mvn -B package` (so formatting + tests must pass), then submits the dependency graph.
