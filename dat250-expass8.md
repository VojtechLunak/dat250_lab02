# Docker assignment - lab07
## Postgres
The port postgres is running on is 5432. One can see that from the EXPOSE command in the postgres dockerfile.

Regarding the -e switch, the container documentations mentions, that you need to specify POSTGRES_PASSWORD:

*You must specify POSTGRES_PASSWORD to a non-empty value for the csuperuser. For example, "-e POSTGRES_PASSWORD=password" on "docker run".*

After correctly running the container I was able to connect to the postgres database via IntelliJ IDEA Data source feature. I created jpa_client user with the password 'secret'.

## Creating my own docker image
Regarding the second part, everything went well. The only issue was that the jpa_client user did not have the needed permissions to create tables in the public schema of postgres. I connected once more as the postgres superuser and added those permissions with GRANT sql queries. Another issue was finding the right base images as the underlying environment for the Poll app, but after some research I found the ones which did have the desired JDK (>=21) installed.

The final working Dockerfile looks like this:

```dockerfile
# Stage 1: Build the application
FROM gradle:8.10-jdk21 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle.kts /app/build.gradle.kts
COPY settings.gradle.kts /app/settings.gradlekts

# Copy the application source code
COPY src /app/src

# Build the application
RUN ./gradlew bootJar

# Stage 2: Create the final image
FROM eclipse-temurin:21-jre-jammy

# Create a non-root user
RUN useradd -m springuser

# Set the working directory
WORKDIR /app

# Copy the built application from the first stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Change ownership of the application files
RUN chown -R springuser:springuser /app

# Switch to the non-root user
USER springuser

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
```