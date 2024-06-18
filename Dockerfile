#FROM gradle:7-jdk17 AS build
#
#
#ENV APP_HOME=/home/gradle/src
#WORKDIR $APP_HOME
#
#COPY --chown=gradle:gradle . $APP_HOME
#
#CMD ["gradle","--no-daemon", "run"]


FROM gradle:7-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle buildFatJar --no-daemon



FROM openjdk:17

EXPOSE 8080:8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar

ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]