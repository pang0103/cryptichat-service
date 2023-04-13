FROM gradle:7.6.1-jdk17-alpine

COPY . /app
WORKDIR /app

RUN gradle build --no-daemon

EXPOSE 8080 9999

CMD ["java", "-jar", "build/libs/socket-test-0.0.1-SNAPSHOT.jar"]