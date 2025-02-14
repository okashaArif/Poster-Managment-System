FROM openjdk:17

COPY target/payment.jar payment.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/payment.jar"]