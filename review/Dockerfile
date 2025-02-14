FROM openjdk:17

COPY target/review.jar review.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/review.jar"]