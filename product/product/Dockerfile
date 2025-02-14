FROM openjdk:17

COPY target/product.jar product.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/product.jar"]