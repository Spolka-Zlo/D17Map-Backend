#FROM gradle:7-jdk17 AS build
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle buildFatJar --no-daemon
#
#FROM openjdk:17
#EXPOSE 8080:8080
#RUN mkdir /app
#COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar
#ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]


# Use AdoptOpenJDK 11 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle files for dependencies resolution
COPY build.gradle.kts settings.gradle.kts gradlew gradle.properties /app/
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Run Gradle to download dependencies. This step is separated to leverage Docker cache
# RUN ./gradlew resolveDependencies --no-daemon

# Build the application without running tests
RUN ./gradlew build -x test --no-daemon

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
CMD ["./gradlew", "run", "--no-daemon"]
