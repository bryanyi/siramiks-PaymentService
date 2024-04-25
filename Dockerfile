FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} paymentservice.jar

# start the jar file
ENTRYPOINT ["java", "-jar", "/paymentservice.jar"]

# expose port
EXPOSE 8082
