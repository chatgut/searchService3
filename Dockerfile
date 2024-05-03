FROM eclipse-temurin:22-alpine
VOLUME /tmp
COPY taget/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]