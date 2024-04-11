FROM openjdk:21-slim
WORKDIR /app
COPY target/bl-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-Dspring.profiles.active=k8s","-jar","app.jar"]