FROM openjdk:17

COPY target/user.jar user.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/user.jar"]