FROM gradle:7.5-jdk18-alpine
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:19-jdk-alpine
VOLUME /stronghold-rsocket-server-service
EXPOSE 7000
RUN mkdir /app
ARG JAR_FILE_NAME=/app/app.jar
COPY --from=build /home/gradle/src/build/libs/rsocket-server-*.jar ${JAR_FILE_NAME}
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
