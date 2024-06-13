FROM gradle:7-jdk17 AS build


ENV APP_HOME=/home/gradle/src
WORKDIR $APP_HOME

COPY --chown=gradle:gradle . $APP_HOME

CMD ["gradle","--no-daemon", "run"]
