FROM openjdk:17

EXPOSE 8080

ARG JAR_FILE=incident-service-1.0.0.jar

ADD incident-service/target/${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
