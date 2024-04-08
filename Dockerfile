FROM openjdk:21-slim
COPY target/bl-0.0.1-SNAPSHOT.jar bl.jar
ENTRYPOINT ["java","-jar","bl.jar"]