# Set the base image to use
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew /app/
COPY gradle /app/gradle

RUN chmod +x /app/gradlew
RUN /app/gradlew --version

COPY src /app/src
# Build the application using Gradle
RUN /app/gradlew build -x test

EXPOSE 8080

# Set the entry point for the container to run the application
ENTRYPOINT ["java", "-jar", "./build/libs/socket-test-0.0.1-SNAPSHOT.jar.jar"]