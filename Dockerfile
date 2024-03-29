FROM eclipse-temurin:17.0.10_7-jre-focal as build
WORKDIR /app
COPY  build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]