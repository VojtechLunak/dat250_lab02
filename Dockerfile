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